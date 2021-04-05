package com.olcertugba.myplaylist.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.ui.home.GenreViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var genreViewModel:GenreViewModel
    private val genreAdapter= GenreAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreViewModel=ViewModelProviders.of(this).get(GenreViewModel::class.java)
        genreViewModel.refreshData()

        genre_recycler.layoutManager= GridLayoutManager(context, 2)
        genre_recycler.adapter=genreAdapter

        swipeRefreshLayout.setOnRefreshListener {
            genreLoading.visibility = View.VISIBLE
            genreErrorMessage.visibility = View.GONE
            genre_recycler.visibility = View.GONE
            genreViewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    fun observeLiveData(){
        genreViewModel.genres.observe(this, Observer {
            it?.let {
                genreAdapter.genreListUpdate(it)
                genre_recycler.visibility=View.VISIBLE
            }
        })

        genreViewModel.genreErrorMessage.observe(this, Observer {
            it?.let {
                if (it==true){
                    genreErrorMessage.visibility=View.VISIBLE
                }else{
                    genreErrorMessage.visibility=View.GONE
                }
            }
        })

        genreViewModel.genreLoading.observe(this, Observer {
            it?.let{
                if (it==true){
                    genre_recycler.visibility=View.GONE
                    genreErrorMessage.visibility=View.GONE
                    genreLoading.visibility=View.VISIBLE
                }else{
                    genreLoading.visibility=View.GONE
                }
            }
        })
    }
}