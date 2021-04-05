package com.olcertugba.myplaylist.model.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SearchResponse(
        @SerializedName("data")
        val searchData: List<SearchData>,
        val next: String,
        val total: Int
)
data class SearchData(
        val artist: Artist,
        val cover: String,
        val cover_big: String,
        val cover_medium: String,
        val cover_small: String,
        val cover_xl: String,
        val explicit_lyrics: Boolean,
        val genre_id: Int,
        val id: String,
        val link: String,
        val md5_image: String,
        val nb_tracks: Int,
        val record_type: String,
        val title: String,
        val tracklist: String,
        val type: String
)
@Entity
data  class SearchQuery (
        @PrimaryKey
        var searchId:String= java.util.UUID.randomUUID().toString(),
        var q:String?="",
        var time:Long=System.currentTimeMillis()
)