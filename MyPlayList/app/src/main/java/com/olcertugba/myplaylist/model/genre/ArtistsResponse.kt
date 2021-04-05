package com.olcertugba.myplaylist.model.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
        @SerializedName("data")
        val artistData: List<ArtistData>
)
@Entity(tableName = "artistData")
data class ArtistData(
        @ColumnInfo(name="id")
        @SerializedName("id")
        val artsitId: String,
        @ColumnInfo(name="name")
        @SerializedName("name")
        val artistName: String,
        @ColumnInfo(name="picture")
        @SerializedName("picture")
        val artistPicture: String,
        @SerializedName("picture_big")
        val picture_big: String,
        @SerializedName("picture_medium")
        val picture_medium: String,
        @SerializedName("picture_small")
        val picture_small: String,
        @SerializedName("picture_xl")
        val picture_xl: String,
        @ColumnInfo(name = "radio")
        @SerializedName("radio")
        val artistRadio: Boolean,
        @ColumnInfo(name = "tracklist")
        @SerializedName("tracklist")
        val tracklist: String,
        @ColumnInfo(name = "type")
        @SerializedName("type")
        val artistType: String
){
        @PrimaryKey(autoGenerate = true)
        var genreDetailId : Int = 0

}
