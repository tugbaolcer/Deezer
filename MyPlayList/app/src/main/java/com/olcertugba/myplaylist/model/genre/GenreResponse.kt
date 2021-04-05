package com.olcertugba.myplaylist.model.genre

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

public data class GenreResponse(
        @SerializedName("data")
        val genreData : List<Data>
)
@Entity(tableName = "data")
data class Data(
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var uid: String?,
        @ColumnInfo(name = "name")
        @SerializedName("name")
        var name: String?,
        @ColumnInfo(name = "picture")
        @SerializedName("picture")
        var picture: String?,
        @ColumnInfo(name = "picture_big")
        @SerializedName("picture_big")
        var picture_big: String?,
        @ColumnInfo(name = "picture_medium")
        @SerializedName("picture_medium")
        var picture_medium: String?,
        @ColumnInfo(name = "picture_small")
        @SerializedName("picture_small")
        var picture_small: String?,
        @ColumnInfo(name = "picture_xl")
        @SerializedName("picture_xl")
        var picture_xl: String?,
        @ColumnInfo(name = "type")
        @SerializedName("type")
        var type: String?
) {
        @PrimaryKey(autoGenerate = true)
        var uuid : Int = 0
}