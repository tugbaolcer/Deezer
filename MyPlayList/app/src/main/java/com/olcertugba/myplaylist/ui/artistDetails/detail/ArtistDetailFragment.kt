package com.olcertugba.myplaylist.ui.artistDetails.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.FragmentArtistDetailBinding
import com.olcertugba.myplaylist.model.artistDetail.ArtistAlbumData
import com.olcertugba.myplaylist.model.artistDetail.ArtistDetailResponse
import com.olcertugba.myplaylist.ui.artistDetails.albums.ArtistAlbumsFragment
import com.olcertugba.myplaylist.ui.artistDetails.related.ArtistRelatedFragment
import com.olcertugba.myplaylist.util.Env
import kotlinx.android.synthetic.main.fragment_artist_detail.*
import kotlinx.android.synthetic.main.fragment_genre_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

class ArtistFragment : Fragment() {
    private lateinit var artistDetailViewModel: ArtistDetailViewModel
    var id:String = "0"
    val tabList:MutableList<String> = mutableListOf("Albums", "Related Details")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistDetailViewModel= ViewModelProviders.of(this).get(ArtistDetailViewModel::class.java)
        arguments?.let {
            id = it.getString(Env.BUND_ID).let { s->
                if(s.isNullOrEmpty()) "0" else s
            }
        }
        Timber.d("artist list - artistdetails : $id")
        val viewAdapter = ADCategories(activity?.supportFragmentManager!!, tabList, id)
        viewPager.adapter=viewAdapter
        tl_categories.setupWithViewPager(viewPager)
        artistDetailViewModel.getArtistDetailNetwork(id)
        observeLiveData()

    }
    fun observeLiveData(){
        artistDetailViewModel.artistDetail.observe(this, {
            it?.let {
            }
        })
    }

}

class ADCategories(fragmentManager: FragmentManager,
                   val items:MutableList<String>,val id:String
):FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment = when(position){
        0-> ArtistAlbumsFragment(id)
        else -> ArtistRelatedFragment(id)
    }

    override fun getCount(): Int = items.size

    override fun getPageTitle(position: Int): CharSequence? = items[position]

    override fun getItemId(position: Int): Long =  View.generateViewId().toLong()

}
