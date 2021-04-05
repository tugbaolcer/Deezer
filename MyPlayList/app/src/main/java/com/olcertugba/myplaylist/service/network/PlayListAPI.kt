package com.olcertugba.myplaylist.service.network

import com.olcertugba.myplaylist.model.album.AlbumDetailsResponse
import com.olcertugba.myplaylist.model.artistDetail.ArtistDetailResponse
import com.olcertugba.myplaylist.model.genre.GenreResponse
import com.olcertugba.myplaylist.model.artistDetail.ArtistAlbumResponse
import com.olcertugba.myplaylist.model.artistDetail.ArtistRelatedResponse
import com.olcertugba.myplaylist.model.genre.ArtistsResponse
import com.olcertugba.myplaylist.model.search.SearchResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayListAPI {

    //HomeFragment
    @GET("genre")
    fun fetchGenreList(): Single<GenreResponse>

    //GenreDetailListFragment
    @GET("genre/{genreId}/artists")
    fun fetchDetailList(@Path("genreId") genreId:String)
            :Single<ArtistsResponse>

    //ArtistDetailFragment
    @GET("artist/{artistId}")
    fun fetchArtistDetails(@Path("artistId") artistId:String)
            : Single<ArtistDetailResponse>

    //ArtistAlbumsFragment
    @GET("artist/{artistId}/albums")
    fun fetchArtistAlbum(@Path("artistId") artistId: String)
            :Single<ArtistAlbumResponse>

    //ArtistRelatedFragment
    @GET("artist/{artistId}/related")
    fun fetchArtistRelated(@Path("artistId") artistId: String)
            :Single<ArtistRelatedResponse>

    //AlbumDetailFragment
    @GET("album/{albumId}/tracks")
    fun fetchAlbumDetail(@Path("albumId") albumId:String)
            :Single<AlbumDetailsResponse>

    @GET("search/album")
    fun fetchSearchAlbum(@Query("q") q:String)
            : Single<SearchResponse>

}