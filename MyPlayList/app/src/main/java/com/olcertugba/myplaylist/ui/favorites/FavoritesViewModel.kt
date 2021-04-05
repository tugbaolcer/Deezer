package com.olcertugba.myplaylist.ui.favorites

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.service.local.DeezerDatabase
import com.olcertugba.myplaylist.ui.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class FavoritesViewModel(application: Application): BaseViewModel(application) {
    var favorites= MutableLiveData<List<AlbumData>>()

    private fun dataShow(albumFavoriteList : List<AlbumData>){
        favorites.value = albumFavoriteList
    }

   fun fetchFavorites(){
        launch {
            val response =DeezerDatabase(getApplication()).deezerDao().getFavorites()
            Timber.d("response : $response")
            if(!response.isNullOrEmpty()){
                dataShow(response) }
        }
    }

}