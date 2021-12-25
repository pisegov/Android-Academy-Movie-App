package com.myaxa.academycourse.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myaxa.academycourse.data.local.daos.MoviesDao
import com.myaxa.academycourse.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class/*, MovieDetailsEntity::class*/], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
//    abstract fun movieDetailsDao(): MovieDetailsDao

    companion object {
        private const val DATABASE_NAME = "MoviesDatabase.db"
        fun createInstance(
            applicationContext: Context,
        ): AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

}