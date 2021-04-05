package com.olcertugba.myplaylist.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.util.Env
import com.olcertugba.myplaylist.databinding.CardItemGenreDetailBinding
import com.olcertugba.myplaylist.model.genre.ArtistData
import kotlinx.android.synthetic.main.card_item_gender.view.*

class GenreDetailAdapter:RecyclerView.Adapter<GenreDetailAdapter.GenreDetaiViewHolder>(){
    private val items:MutableList<ArtistData> = mutableListOf()
    class GenreDetaiViewHolder(val view:CardItemGenreDetailBinding):RecyclerView.ViewHolder(view.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreDetaiViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val databinding=DataBindingUtil.inflate<CardItemGenreDetailBinding>(inflater, R.layout.card_item_genre_detail, parent, false)
        return GenreDetaiViewHolder(databinding).apply {
            databinding.root.setOnClickListener {
                val position = adapterPosition.takeIf { p-> p != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener

                it.findNavController().navigate(
                    R.id.action_genreListFragment_to_artistFragment,
                    bundleOf(
                        Env.BUND_ID to items[position].artsitId,
                        Env.BUND_NAME to items[position].artistName )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: GenreDetaiViewHolder, position: Int) {
         holder.view.apply {
             genreDetail = items[position]
             executePendingBindings()
         }
    }

    override fun getItemCount(): Int = items.size

    fun genreDetailListUpdate(newGenreDetailList:List<ArtistData>){
        val previousSize = items.size
        items.addAll(newGenreDetailList)
        notifyItemRangeChanged(previousSize,items.size)
    }



}