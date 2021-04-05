package com.olcertugba.myplaylist.ui.search

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.olcertugba.myplaylist.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    val searchAdapter= SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel= ViewModelProviders.of(this).get(SearchViewModel::class.java)
             val recentAdapter= RecentSearchAdapter(object : RecentSearchAdapter.RecentSearchListener{
                override fun recentSearchListener(query: String) {
                    aet_search.text = Editable.Factory.getInstance().newEditable(query)
                    searchViewModel.queryLiveData.value = query
                }
            })
        recent_search.adapter=recentAdapter
        recent_search.layoutManager=LinearLayoutManager(context)
        rv_search.adapter=searchAdapter
        rv_search.layoutManager=LinearLayoutManager(context)
        searchViewModel.fetchRecentSearch()
        searchViewModel.fetchSearch()
        observeLiveData()
    }
    fun observeLiveData() {
        searchViewModel.search.observe(this, Observer {
            it?.let {
                searchAdapter.searchListUpdate(it)
                genre_recycler.visibility = View.VISIBLE
            }
        })
    }
}


