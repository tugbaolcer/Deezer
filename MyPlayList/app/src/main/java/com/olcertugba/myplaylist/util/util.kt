package com.olcertugba.myplaylist.util

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.olcertugba.myplaylist.R
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.mediaPlayer.MediaPlayerState
import com.olcertugba.myplaylist.ui.favorites.FavoritesAdapter
import com.squareup.picasso.Picasso
import timber.log.Timber

fun ImageView.glideImageDowloand(url: String?, placeholder: CircularProgressDrawable){

    val glideOptions = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(glideOptions).load(url).into(this)
}

fun placeholderYap(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
@BindingAdapter("isGoneMediaPlayer")
fun bindingIsGoneMediaPlayer(view: View, observerIsGone: ObservableBoolean){
    Timber.d("isGoneMediaPlayer : ${observerIsGone.get()}")
    view.isGone = when(view.id){
        R.id.cl_media_player -> !observerIsGone.get()
        else-> observerIsGone.get()
    }
}

@BindingAdapter("iconPlayPause")
fun iconPlayPause(view: ImageButton, state: MutableLiveData<MediaPlayerState>){
    view.apply {
        setImageResource(
            when (state.value) {
                MediaPlayerState.PLAYING -> R.drawable.ic_pause
                else -> R.drawable.ic_play
            }
        )
        adjustViewBounds = true
        refreshDrawableState()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url: String?){
    view.glideImageDowloand(url, placeholderYap(view.context))
}


@BindingAdapter("adapterTablayout")
fun bindingTabLayoutAdapter(view: TabLayout, viewPager: ViewPager){
    view.setupWithViewPager(viewPager)
}

@BindingAdapter("adapterViewPager")
fun bindingViewPagerAdapter(view: ViewPager, adapter: FragmentPagerAdapter){
    view.adapter = adapter
}

@BindingAdapter("isGoneFavoriteLayout")
fun bindingIsGoneFavoriteLayout(view: View, results: MutableLiveData<List<AlbumData>>){
    Timber.d("bindingIsGoneFavoriteLayout : ${results.value}")
    when(view.id){
        R.id.recyclerViewFavorite -> {
            view.isGone = results.value.isNullOrEmpty()
        }
        else->{
            view.isGone = !results.value.isNullOrEmpty()
        }
    }
}
@BindingAdapter("adapterFavoritesList")
fun bindingFavoritesList(view: RecyclerView, results:MutableLiveData<List<AlbumData>>) {
    if (!results.value.isNullOrEmpty()) {
        Timber.d("Favorites result :${results.value} ")
        (view.adapter as FavoritesAdapter).addFavoritesList(results.value!!)
    }
}