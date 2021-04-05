package com.olcertugba.myplaylist.model.album


import com.google.gson.annotations.SerializedName

data class Artist(
    val id: String,
    val name: String,
    val tracklist: String,
    val type: String,
    @SerializedName("picture_medium")
    var picture_medium:String?
)