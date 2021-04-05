package com.olcertugba.myplaylist.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olcertugba.myplaylist.model.album.AlbumData
import com.olcertugba.myplaylist.model.genre.Data
import com.olcertugba.myplaylist.model.search.SearchQuery

@Database(entities = [Data::class, AlbumData::class, SearchQuery::class], version = 4)
abstract class DeezerDatabase : RoomDatabase() {

    abstract fun deezerDao() : DeezerDao

    //Singleton
    companion object {

        @Volatile private var instance : DeezerDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                DeezerDatabase::class.java,"Deezerdatabase")
                .fallbackToDestructiveMigration()
                .build()

    }

}