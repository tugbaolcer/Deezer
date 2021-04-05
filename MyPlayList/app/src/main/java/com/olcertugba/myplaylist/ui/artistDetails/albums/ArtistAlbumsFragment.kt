package com.olcertugba.myplaylist.ui.artistDetails.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.olcertugba.myplaylist.R
import kotlinx.android.synthetic.main.fragment_artist_albums.*

class ArtistAlbumsFragment(private val artistID:String) : Fragment() {
    private lateinit var artistAlbumsViewModel: ArtistAlbumsViewModel
    private val artistAlbumAdpater= ArtistAlbumAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistAlbumsViewModel= ViewModelProviders.of(this).get(ArtistAlbumsViewModel::class.java)
        artistAlbumRecyclerView.layoutManager= LinearLayoutManager(context)
        artistAlbumRecyclerView.adapter=artistAlbumAdpater
        artistAlbumsViewModel.getArtistAlbumNetwork(artistID)
        observeLiveData()

    }

    fun observeLiveData(){
        artistAlbumsViewModel.artistAlbum.observe(this, {
            it?.let {
                artistAlbumAdpater.artistAlbumListUpdate(it)
                artistAlbumRecyclerView.visibility = View.VISIBLE
            }
        })
    }

}