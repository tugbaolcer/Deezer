package com.olcertugba.myplaylist.ui.genre

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.genre.ArtistData
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class GenreDetailViewModel(application: Application): BaseViewModel(application) {
    val genreDetail= MutableLiveData<List<ArtistData>>()
    private val playListApiService = PlayListAPIService()
    private val disposable = CompositeDisposable()//tek kullanımlık (rxjava)

     fun getDataDetailNetwork(id:String){
        disposable.add(
            playListApiService.getGenreDetail(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ArtistData>>() {
                    override fun onSuccess(t: List<ArtistData>) {
                        //Başarılı olursa
                        genreDetail.value = t
                        Toast.makeText(getApplication(),"verileri Internet'ten Aldık", Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )

    }

}
