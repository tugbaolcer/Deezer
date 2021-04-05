package com.olcertugba.myplaylist.ui.artistDetails.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemArtistAlbumBinding
import com.olcertugba.myplaylist.model.artistDetail.ArtistAlbumData
import com.olcertugba.myplaylist.util.Env

class ArtistAlbumAdapter:RecyclerView.Adapter<ArtistAlbumAdapter.ArtistAlbumViewHolder>() {

    private val items:MutableList<ArtistAlbumData> = mutableListOf()

    class ArtistAlbumViewHolder(val view: CardItemArtistAlbumBinding):RecyclerView.ViewHolder(view.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAlbumViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val artistAlbumDataBinding=DataBindingUtil.inflate<CardItemArtistAlbumBinding>(inflater, R.layout.card_item_artist_album, parent, false)
        return ArtistAlbumViewHolder(artistAlbumDataBinding).apply {
            artistAlbumDataBinding.root.setOnClickListener {
                val position = adapterPosition.takeIf { p-> p != RecyclerView.NO_POSITION }
                        ?: return@setOnClickListener
                it.findNavController().navigate(
                        R.id.action_artistFragment_to_albumFragment,
                        bundleOf(
                                Env.BUND_ID to items[position].id,
                                Env.BUND_NAME to items[position].title )
                    )
            }
        }
    }
    override fun onBindViewHolder(holder: ArtistAlbumViewHolder, position: Int) {
        holder.view.apply {
            album=items[position]
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int=items.size

    fun artistAlbumListUpdate(artistAlbumList:List<ArtistAlbumData>){
        val previousSize = items.size
        items.addAll(artistAlbumList)
        notifyItemRangeChanged(previousSize,items.size)
    }
}