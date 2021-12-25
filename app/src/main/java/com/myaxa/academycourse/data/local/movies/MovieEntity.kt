package com.myaxa.academycourse.data.local.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "storyLine")
    val storyLine: String = "",
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String = "",
    @ColumnInfo(name = "pgAge")
    val pgAge: Int = 0,
    @ColumnInfo(name = "genres")
    val genres: String = "",
    @ColumnInfo(name = "runningTime")
    val runningTime: Int = 0,
    @ColumnInfo(name = "reviewCount")
    val reviewCount: Int = 0,
    @ColumnInfo(name = "isLiked")
    val isLiked: Boolean = false,
    @ColumnInfo(name = "rating")
    val rating: Int = 0,
)