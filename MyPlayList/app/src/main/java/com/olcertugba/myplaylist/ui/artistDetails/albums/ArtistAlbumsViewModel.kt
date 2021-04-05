package com.olcertugba.myplaylist.ui.artistDetails.albums

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.artistDetail.ArtistAlbumData
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ArtistAlbumsViewModel(application: Application):BaseViewModel(application) {
    val artistAlbum=MutableLiveData<List<ArtistAlbumData>>()
    private val playListApiService=PlayListAPIService()
    private val disposable=CompositeDisposable()

    fun getArtistAlbumNetwork(id:String){
        disposable.add(
                playListApiService.getArtistAlbum(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :DisposableSingleObserver<List<ArtistAlbumData>>(){
                            override fun onSuccess(t: List<ArtistAlbumData>) {
                                artistAlbum.value=t
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }

                        }
                        )
        )
    }
}