package com.olcertugba.myplaylist.service.network

import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.album.AlbumDetailsResponse
import com.olcertugba.myplaylist.model.artistDetail.ArtistDetailResponse
import com.olcertugba.myplaylist.model.artistDetail.ArtistAlbumData
import com.olcertugba.myplaylist.model.artistDetail.ArtistRelatedData
import com.olcertugba.myplaylist.model.genre.ArtistData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.model.search.SearchData
import com.olcertugba.myplaylist.model.search.SearchQuery
import com.olcertugba.myplaylist.model.search.SearchResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PlayListAPIService {
    private val BASE_URL="https://api.deezer.com/"
    private val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val api: PlayListAPI =retrofit.create(PlayListAPI::class.java)

    fun getGenreData(): Single<List<Data>>
    =api.fetchGenreList().map {it.genreData}

    fun getGenreDetail(genreId:String):Single<List<ArtistData>>
    =api.fetchDetailList(genreId).map { it.artistData }

    fun getArtistDetail(artistId:String):Single<List<ArtistDetailResponse>>
    =api.fetchArtistDetails(artistId).map { listOf(it) }

    fun getArtistAlbum(artistId: String):Single<List<ArtistAlbumData>>
    =api.fetchArtistAlbum(artistId).map { it.artistAlbumData }

    fun getArtistRelated(artistId: String):Single<List<ArtistRelatedData>>
    =api.fetchArtistRelated(artistId).map { it.artistRelatedData }

    fun getAlbumDetail(albumId: String):Single<List<AlbumData>>
    =api.fetchAlbumDetail(albumId).map { it.albumData }

    fun getSearchAlbum(q:String):Single<List<SearchData>>
    = api.fetchSearchAlbum(q).map{it.searchData}

}

