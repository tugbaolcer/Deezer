package com.olcertugba.myplaylist.ui.album

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.service.local.DeezerDatabase
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class AlbumDetailsViewModel(application: Application):BaseViewModel(application) {
    val album= MutableLiveData<List<AlbumData>>()
    private val playListApiService= PlayListAPIService()
    private val disposable= CompositeDisposable()

    private fun dataShow(albumList : List<AlbumData>){
        album.value = albumList
    }

    fun favoritedToTrack(data: AlbumData) {
        launch {
            val insertFavoriteData=DeezerDatabase(getApplication()).deezerDao().insertTrack(data)
        }
    }

    fun getAlbumNetwork(id:String){
        disposable.add(
                playListApiService.getAlbumDetail(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<AlbumData>>(){
                            override fun onSuccess(t: List<AlbumData>) {
                                album.value=t
                            }
                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }

                        }
                        )
        )
    }
}