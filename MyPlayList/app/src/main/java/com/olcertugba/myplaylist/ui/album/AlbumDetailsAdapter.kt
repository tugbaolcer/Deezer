package com.olcertugba.myplaylist.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.databinding.CardItemAlbumBinding
import com.olcertugba.myplaylist.databinding.CardItemGenderBinding
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.util.Env
import kotlinx.android.synthetic.main.card_item_album.view.*

class AlbumDetailsAdapter(val listener: AlbumDetailClickListener): RecyclerView.Adapter<AlbumDetailsAdapter.AlbumViewHolder>(){
    private val items:MutableList<AlbumData> = mutableListOf()

    class AlbumViewHolder(var view: CardItemAlbumBinding): RecyclerView.ViewHolder(view.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val albumDetailDataBinding= DataBindingUtil.inflate<CardItemAlbumBinding>(inflater, R.layout.card_item_album, parent, false)

        return AlbumViewHolder(albumDetailDataBinding).apply {
            albumDetailDataBinding.root.albumDetailCardView.setOnClickListener{
                val position=adapterPosition
                listener.onItemClickListener(it, position, items)
            }

            albumDetailDataBinding.root.albumDetail_fav.setOnClickListener{
                val favPosition=adapterPosition.takeIf { p->p != RecyclerView.NO_POSITION }
                val favItem=items[favPosition!!]
                listener.onItemClickListener(it, favPosition, favItem)
                Toast.makeText(albumDetailDataBinding.root.context,"Add to Favorites!", Toast.LENGTH_LONG).show()
            }

            albumDetailDataBinding.root.albumDetail_share.setOnClickListener {
                val sharePosition=adapterPosition.takeIf { p->p != RecyclerView.NO_POSITION }
                val shareItem=items[sharePosition!!]
                listener.onItemClickListener(it, sharePosition, shareItem)
            }
        }
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        holder.view.apply {
            album=items[position]
            executePendingBindings()
        }

    }

    override fun getItemCount(): Int = items.size

    fun albumListUpdate(albumList: List<AlbumData>){
        val previousSize = items.size
        items.addAll(albumList)
        notifyItemRangeChanged(previousSize,items.size)
    }

}