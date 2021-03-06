package com.olcertugba.myplaylist.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemFavoriteBinding
import com.olcertugba.myplaylist.model.album.AlbumData
import timber.log.Timber

class FavoritesAdapter(val listener:OnClick): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    interface OnClick{
        fun onItemClickListener(v: View, trackPos:Int, item: List<AlbumData>)
    }

    private val items: MutableList<AlbumData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
            .inflate<CardItemFavoriteBinding>(inflater, R.layout.card_item_favorite, parent, false)
        return FavoritesViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition
                listener.onItemClickListener(it, position, items)
            }
        }
    }
    fun addFavoritesList(favorites: List<AlbumData>) {
        Timber.d("Favorites addFavorites $favorites")
        val previousSize = items.size
        items.addAll(favorites)
        // Timber.d("GenreAdapter  size : $previousSize  \t genreList size : ${genreList.size} item size : ${items.size} ")
        notifyItemRangeChanged(previousSize, items.size)
    }
    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        //Timber.d("Items$position ${items[position].toString()}")
        holder.binding.apply {
            Timber.d("binding..")
            favorite = items[position]
            executePendingBindings()
        }
    }
    override fun getItemCount(): Int = items.size
    class FavoritesViewHolder(val binding: CardItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)
}