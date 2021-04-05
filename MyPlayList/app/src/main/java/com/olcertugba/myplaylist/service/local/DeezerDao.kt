package com.olcertugba.myplaylist.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.genre.ArtistData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.model.genre.GenreResponse
import com.olcertugba.myplaylist.model.search.SearchQuery

@Dao
interface DeezerDao {

    @Insert //Room, sınıfından gelir.
    suspend fun insertAll(vararg data: Data):List<Long>

    @Query("SELECT * FROM data")
    suspend fun getGenreList():List<Data>


    @Query("Select * from data where uuid=:id")
    suspend fun getData(id:String):Data

    @Query("Delete from data")
    suspend fun deleteAllGender()

    /* insert to query */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuery(q:SearchQuery)

    /* recent search */
    @Query("SELECT * FROM SearchQuery ORDER BY time")
    suspend fun getQueryList():List<SearchQuery>

    /* insert to track */
    @Insert
    suspend fun insertTrack(vararg albumData: AlbumData):List<Long>

    /* recent favorites */
    @Query("SELECT * FROM AlbumData ORDER BY fav_time")
    suspend fun getFavorites():List<AlbumData>

}