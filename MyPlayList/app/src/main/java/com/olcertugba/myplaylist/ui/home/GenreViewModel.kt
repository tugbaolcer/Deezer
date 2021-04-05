package com.olcertugba.myplaylist.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.service.local.DeezerDatabase
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import com.olcertugba.myplaylist.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class GenreViewModel(application: Application): BaseViewModel(application) {
    val genres= MutableLiveData<List<Data>>()
    val genreErrorMessage= MutableLiveData<Boolean>()
    val genreLoading= MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L
    private val playListApiService = PlayListAPIService()
    private val disposable = CompositeDisposable()//tek kullanımlık (rxjava)
    private val ozelSharedPreferences= OzelSharedPreferences(getApplication())

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite'tan çek
            verileriSQLitetanAl()
        } else {
            getDataNetwork()
        }
    }
    fun refreshFromInternet(){
        getDataNetwork()
    }
    private fun verileriSQLitetanAl(){
        genreLoading.value = true
        launch {

            val deezerdatabase = DeezerDatabase(getApplication()).deezerDao().getGenreList()
            dataShow(deezerdatabase)
            Toast.makeText(getApplication(),"Verileri Room'dan Aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataNetwork(){
        genreLoading.value = true
        disposable.add(
                playListApiService.getGenreData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Data>>() {
                            override fun onSuccess(t: List<Data>) {
                                //Başarılı olursa
                                sqliteSakla(t)
                                Toast.makeText(getApplication(),"verileri Internet'ten Aldık", Toast.LENGTH_LONG).show()
                            }
                            override fun onError(e: Throwable) {
                                //Hata alırsak
                                genreErrorMessage.value = true
                                genreLoading.value = false
                                e.printStackTrace()
                            }
                        })
        )

    }

    private fun dataShow(genreList : List<Data>){
        genres.value = genreList
        genreErrorMessage.value = false
        genreLoading.value = false
    }

    private fun sqliteSakla(dataList: List<Data>){

        launch {

            val dao = DeezerDatabase(getApplication()).deezerDao()
            dao.deleteAllGender()
            val uuidListesi = dao.insertAll(*dataList.toTypedArray())
            var i = 0
            while (i < dataList.size){
                dataList[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            dataShow(dataList)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}