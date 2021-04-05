package com.olcertugba.myplaylist.ui.artistDetails.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.artistDetail.ArtistDetailResponse
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ArtistDetailViewModel(application: Application): BaseViewModel(application) {
    val artistDetail=MutableLiveData<List<ArtistDetailResponse>> ()
    private val playListApiService= PlayListAPIService()
    private val disposable=CompositeDisposable()

    fun getArtistDetailNetwork(artistId:String){
        disposable.add(
                playListApiService.getArtistDetail(artistId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<ArtistDetailResponse>>() {
                            override fun onSuccess(t: List<ArtistDetailResponse>) {
                                //Başarılı olursa
                                artistDetail.value = t
                            }
                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                            }
                        })
        )

    }
}
