package com.olcertugba.myplaylist.ui.artistDetails.related

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemArtistRelatedBinding
import com.olcertugba.myplaylist.model.artistDetail.ArtistRelatedData
import com.olcertugba.myplaylist.util.Env

class ArtistRelatedAdapter:RecyclerView.Adapter<ArtistRelatedAdapter.ArtistRelatedViewHolder>() {

    private val items: MutableList<ArtistRelatedData> = mutableListOf()

    class ArtistRelatedViewHolder(val view:CardItemArtistRelatedBinding):RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistRelatedViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val artistRelatedDataBinding=DataBindingUtil.inflate<CardItemArtistRelatedBinding>(inflater, R.layout.card_item_artist_related, parent, false)
        return ArtistRelatedViewHolder(artistRelatedDataBinding).apply {
            artistRelatedDataBinding.root.setOnClickListener {
                val position=adapterPosition.takeIf { p->p != RecyclerView.NO_POSITION }
                        ?:return@setOnClickListener
                it.findNavController().navigate(
                        R.id.action_artistFragment_self,
                        bundleOf(
                                Env.BUND_ID to items[position].id,
                                Env.BUND_NAME to items[position].name
                        )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ArtistRelatedViewHolder, position: Int) {
        holder.view.apply {
            related=items[position]
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int=items.size

    fun artistRelatedListUpdate(artistRelatedList:List<ArtistRelatedData>){
        val previousSize=items.size
        items.addAll(artistRelatedList)
        notifyItemRangeChanged(previousSize, items.size)
    }

}