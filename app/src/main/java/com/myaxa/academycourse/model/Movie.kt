package com.myaxa.academycourse.model

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val storyLine: String = "",
    val imageUrl: String = "",
    val actors: List<Actor> = listOf(),
    val pgAge: Int = 0,
    val genres: List<Genre> = listOf(),
    val runningTime: Int = 0,
    val reviewCount: Int = 0,
    val isLiked: Boolean = false,
    val rating: Int = 0,
)