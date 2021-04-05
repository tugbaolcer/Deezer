package com.olcertugba.myplaylist.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.util.Env
import kotlinx.android.synthetic.main.fragment_genre_list.*
import timber.log.Timber


class GenreListFragment : Fragment() {

    private lateinit var genreDetailViewModel: GenreDetailViewModel
    private val genreDetailAdpater= GenreDetailAdapter()
    private var id:String = "0"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genreDetailViewModel=ViewModelProviders.of(this).get(GenreDetailViewModel::class.java)
        genreDetailRecyclerView.layoutManager=GridLayoutManager(context,2)
        genreDetailRecyclerView.adapter=genreDetailAdpater
        arguments?.let {
            id = it.getString(Env.BUND_ID).let { s->
                if(s.isNullOrEmpty()) "0" else s
            }
        }
        Timber.d("artist list - genreId : $id")
        genreDetailViewModel.getDataDetailNetwork(id)
        observeLiveData()

    }

    fun observeLiveData(){
        genreDetailViewModel.genreDetail.observe(this, {
            it?.let {
                genreDetailAdpater.genreDetailListUpdate(it)
                genreDetailRecyclerView.visibility = View.VISIBLE
            }
        })
    }

}