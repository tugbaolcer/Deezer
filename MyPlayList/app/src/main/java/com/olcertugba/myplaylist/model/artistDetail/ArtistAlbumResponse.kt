package com.olcertugba.myplaylist.model.artistDetail

import com.google.gson.annotations.SerializedName

data class ArtistAlbumResponse (
    @SerializedName("data")
    val `artistAlbumData`: List<ArtistAlbumData>,
    val next: String="",
    val total: Int
)
data class ArtistAlbumData(
        val cover: String="",
        val cover_big: String="",
        val cover_medium: String="",
        val cover_small: String="",
        val cover_xl: String="",
        val explicit_lyrics: Boolean?=false,
        val fans: Int=0,
        val genre_id: Int=0,
        val id: String="",
        val link: String="",
        val md5_image: String="",
        val record_type: String="",
        val release_date: String="",
        val title: String="",
        val tracklist: String="",
        val type: String=""
)