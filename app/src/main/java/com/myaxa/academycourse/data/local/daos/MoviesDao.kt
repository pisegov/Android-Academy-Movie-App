package com.myaxa.academycourse.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myaxa.academycourse.data.local.entities.MovieEntity

@Dao
interface MoviesDao {
    @Query("select * from movies")
    fun getAll(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(moviesList: List<MovieEntity>)
}