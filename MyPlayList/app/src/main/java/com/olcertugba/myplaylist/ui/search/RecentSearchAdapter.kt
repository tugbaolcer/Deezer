package com.olcertugba.myplaylist.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemSearchBinding
import com.olcertugba.myplaylist.databinding.ItemSearchBinding
import com.olcertugba.myplaylist.model.search.SearchQuery
import timber.log.Timber

class RecentSearchAdapter(val listener: RecentSearchListener): RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>(){
    interface RecentSearchListener{
        fun recentSearchListener(query: String)
    }
    private val items: MutableList<SearchQuery> = mutableListOf()

    class RecentSearchViewHolder(var view : ItemSearchBinding):RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
      val inflater=LayoutInflater.from(parent.context)
        val recentSearchDataBinding=DataBindingUtil.inflate<ItemSearchBinding>(inflater, R.layout.item_search, parent, false)
        return RecentSearchViewHolder(recentSearchDataBinding).apply {
            recentSearchDataBinding.root.setOnClickListener {
                val position = adapterPosition
                Timber.d("position : $position")
                listener.recentSearchListener(items[position].q.toString())
                Toast.makeText(recentSearchDataBinding.root.context,"Searching ${items[position].q}", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun addRecentSearch(searchList: List<SearchQuery>) {
        Timber.d("searchList : ${searchList.toString()}")
        val previousSize = items.size
        items.addAll(searchList)
        // Timber.d("GenreAdapter  size : $previousSize  \t genreList size : ${genreList.size} item size : ${items.size} ")
        notifyItemRangeChanged(previousSize, items.size)
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
       holder.view.apply {
           search=items[position]
           executePendingBindings()
       }
    }

    override fun getItemCount(): Int=items.size

}

