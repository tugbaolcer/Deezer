package com.olcertugba.myplaylist.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.activity.PlayListHomeActivity
import com.olcertugba.myplaylist.activity.PlayListHomeViewModel
import com.olcertugba.myplaylist.model.album.AlbumData
import kotlinx.android.synthetic.main.fragment_album.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var favoriteViewModel:FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel=ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        val favoriteAdapter = FavoritesAdapter(object : FavoritesAdapter.OnClick {
            override fun onItemClickListener(v: View,trackPos:Int, albumList:List<AlbumData>) {
                ((this@FavoritesFragment).requireActivity() as PlayListHomeActivity).playListHomeViewModel.apply {
                    albumData.value = albumList
                    positionTrack = trackPos
                    isGoneMediaPlayer.set(true)
                    playMusic()
                }
            }
        })
        recyclerViewFavorite.adapter=favoriteAdapter
        favoriteViewModel.fetchFavorites()
    }

}