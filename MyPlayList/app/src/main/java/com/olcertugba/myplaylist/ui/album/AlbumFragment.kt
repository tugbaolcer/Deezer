package com.olcertugba.myplaylist.ui.album

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.activity.PlayListHomeActivity
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.util.Env
import kotlinx.android.synthetic.main.fragment_album.*
import timber.log.Timber

class AlbumFragment : Fragment() {

    private  lateinit var albumDetailViewModel: AlbumDetailsViewModel
    var id = "0"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumDetailViewModel=ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)
        id = arguments?.getString(Env.BUND_ID) ?: "0"
        Timber.d("id : $id")
        val albumDetailsAdapter= AlbumDetailsAdapter(object: AlbumDetailClickListener {
            override fun onItemClickListener(v: View, pos: Int, data: Any) {
                when (v.id) {
                    R.id.albumDetail_fav -> {
                        val item = data as AlbumData
                        albumDetailViewModel.favoritedToTrack(item)
                    }
                    R.id.albumDetail_share -> {
                        val item = data as AlbumData
                        (this@AlbumFragment.requireActivity() as PlayListHomeActivity).apply {
                            val sharingIntent = Intent(Intent.ACTION_SEND)
                                    .putExtra(Intent.EXTRA_SUBJECT, Env.APP_NAME)
                                    .putExtra(Intent.EXTRA_TEXT, item.link)
                            startActivity(Intent.createChooser(sharingIntent, "Share via"))
                        }
                    }
                    R.id.albumDetailCardView -> {
                        val list = data as List<AlbumData>
                        ((this@AlbumFragment).requireActivity() as PlayListHomeActivity).playListHomeViewModel.apply {
                            albumData.value = list
                            positionTrack = pos
                            isGoneMediaPlayer.set(true)
                            playMusic()
                        }

                    }
                }
            }

        })

        albumDetailRecyclerView.adapter=albumDetailsAdapter
        albumDetailViewModel.getAlbumNetwork(id)
        albumDetailViewModel.album.observe(this, {
            it?.let {
                albumDetailsAdapter.albumListUpdate(it)
                albumDetailRecyclerView.visibility=View.VISIBLE
            }
        })

    }

}