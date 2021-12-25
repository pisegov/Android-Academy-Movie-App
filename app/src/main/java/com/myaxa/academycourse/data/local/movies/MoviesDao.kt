package com.myaxa.academycourse.data.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("select * from movies")
    fun getAll(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(moviesList: List<MovieEntity>)
}