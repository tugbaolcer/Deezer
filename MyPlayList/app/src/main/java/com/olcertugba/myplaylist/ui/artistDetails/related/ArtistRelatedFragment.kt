package com.olcertugba.myplaylist.ui.artistDetails.related

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.olcertugba.myplaylist.R
import kotlinx.android.synthetic.main.fragment_artist_related.*

class ArtistRelatedFragment(private val artistID:String) : Fragment() {

    private lateinit var artistRelatedViewModel:ArtistRelatedViewModel
    private val artistRelatedAdapter=ArtistRelatedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_related, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistRelatedViewModel=ViewModelProviders.of(this).get(ArtistRelatedViewModel::class.java)
        artistRelatedRecyclerView.layoutManager=GridLayoutManager(context,2)
        artistRelatedRecyclerView.adapter=artistRelatedAdapter

        artistRelatedViewModel.getArtistRelatedNetwork(artistID)
        observeLiveData()

    }
    fun observeLiveData(){
        artistRelatedViewModel.artistRelated.observe(this,{
            it.let {
                artistRelatedAdapter.artistRelatedListUpdate(it)
                artistRelatedRecyclerView.visibility=View.VISIBLE
            }
        })
    }

}