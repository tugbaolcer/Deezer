package com.olcertugba.myplaylist.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemSearchBinding
import com.olcertugba.myplaylist.model.search.SearchData
import com.olcertugba.myplaylist.util.Env

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    val searchItems:MutableList<SearchData> = mutableListOf()

    class SearchViewHolder(var view: CardItemSearchBinding):RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val searchDataBinding=DataBindingUtil.inflate<CardItemSearchBinding>(inflater, R.layout.card_item_search, parent, false)
        return SearchViewHolder(searchDataBinding).apply {
            view.root.setOnClickListener {
                val position = adapterPosition.takeIf { p -> p != RecyclerView.NO_POSITION } ?: 0

                it.findNavController().navigate(
                        R.id.action_searchFragment_to_albumFragment,
                        bundleOf(
                                Env.BUND_ID to searchItems[position].id,
                                Env.BUND_NAME to searchItems[position].title
                        ))
            }
        }
        }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
       holder.view.apply {
           search=searchItems[position]
       }
    }

    override fun getItemCount(): Int =searchItems.size

    fun searchListUpdate(newSearchList: List<SearchData>){
        val previousSize=searchItems.size
        searchItems.addAll(newSearchList)
        notifyItemRangeChanged(previousSize, searchItems.size)
    }
}

