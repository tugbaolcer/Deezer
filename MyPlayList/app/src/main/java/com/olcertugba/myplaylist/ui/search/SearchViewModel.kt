package com.olcertugba.myplaylist.ui.search

import android.app.Application
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.*
import com.olcertugba.myplaylist.model.search.SearchData
import com.olcertugba.myplaylist.model.search.SearchQuery
import com.olcertugba.myplaylist.service.local.DeezerDatabase
import com.olcertugba.myplaylist.service.network.PlayListAPIService
import com.olcertugba.myplaylist.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(application: Application):BaseViewModel(application){

    var queryLiveData:MutableLiveData<String> = MutableLiveData()
    var recentSearch= MutableLiveData<List<SearchQuery>>()

    val search= MutableLiveData<List<SearchData>>()
    private val playListApiService = PlayListAPIService()
    private val disposable = CompositeDisposable()//tek kullanımlık (rxjava)
    val editorActionListener:TextView.OnEditorActionListener
    init {
        this.editorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Timber.d("action search")
                queryLiveData.value = v.text.toString()
                true
            } else false
        }


    }

    private fun dataQueryShow(genreList : List<SearchQuery>){
        recentSearch.value = genreList
    }

     fun fetchRecentSearch() {
         launch {
             val response = DeezerDatabase(getApplication()).deezerDao().getQueryList()
             Timber.d("response : $response")
             if (!response.isNullOrEmpty()) {
                dataQueryShow(response)
             }
         }
    }

    fun insertSearch(query: SearchQuery){
        launch {
            DeezerDatabase(getApplication()).deezerDao().insertQuery(query)
        }
    }


    //----------------------------------------------------------------------------------------------
    fun getSearchNetwork(q:String){
        insertSearch(SearchQuery(q=q))
        disposable.add(
           playListApiService.getSearchAlbum(q)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SearchData>>() {
                    override fun onSuccess(t: List<SearchData>) {
                        search.value=t
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )

    }
    fun fetchSearch(): LiveData<Unit> {
     return Transformations.map(queryLiveData){
         getSearchNetwork(it)
     }
    }
}
