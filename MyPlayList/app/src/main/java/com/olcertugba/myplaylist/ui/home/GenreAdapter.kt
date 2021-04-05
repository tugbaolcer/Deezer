package com.olcertugba.myplaylist.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.util.Env
import com.olcertugba.myplaylist.databinding.CardItemGenderBinding
import com.olcertugba.myplaylist.model.genre.Data
import kotlinx.android.synthetic.main.card_item_gender.view.*
import java.util.*

class GenreAdapter:RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){
    private val items:MutableList<Data> = mutableListOf()
    class GenreViewHolder(var view: CardItemGenderBinding):RecyclerView.ViewHolder(view.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<CardItemGenderBinding>(inflater, R.layout.card_item_gender, parent, false)

        return GenreViewHolder(view).apply {
            view.root.setOnClickListener {
                val position=adapterPosition.takeIf { p-> p!= NO_POSITION }
                    ?:return@setOnClickListener
                it.findNavController().navigate(
                    R.id.action_homeFragment_to_genreListFragment,
                    bundleOf(
                        Env.BUND_ID to items[position].uid,
                        Env.BUND_NAME to items[position].name)

                )
            }
        }
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {

        holder.view.apply {
            genre=items[position]
            executePendingBindings()
        }

    }

    override fun getItemCount(): Int = items.size

    fun genreListUpdate(newGenreList: List<Data>){
        val previousSize = items.size
        items.addAll(newGenreList)
        notifyItemRangeChanged(previousSize,items.size)
    }

}