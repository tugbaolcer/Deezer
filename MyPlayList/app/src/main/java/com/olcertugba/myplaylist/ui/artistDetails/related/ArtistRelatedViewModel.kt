package com.olcertugba.myplaylist.ui.artistDetails.related

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.artistDetail.ArtistRelatedData
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ArtistRelatedViewModel(application: Application):BaseViewModel(application) {
    val artistRelated=MutableLiveData<List<ArtistRelatedData>>()
    private val playListApiService=PlayListAPIService()
    private val disposable=CompositeDisposable()

    fun getArtistRelatedNetwork(id:String){
        disposable.add(
                playListApiService.getArtistRelated(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object:DisposableSingleObserver<List<ArtistRelatedData>>(){
                            override fun onSuccess(t: List<ArtistRelatedData>) {
                                artistRelated.value=t
                            }

                            override fun onError(e: Throwable) {
                               e.printStackTrace()
                            }

                        })
        )
    }
}