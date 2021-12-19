package com.myaxa.academycourse.model

data class MovieDetails(
    val pgAge: Int = 0,
    val detailImageUrl: String = "",
    val genres: String = "",
    val homepage: String = "",
    val id: Int = 0,
    val storyLine: String = "",
    val rating: Int = 0,
    val posterPath: String = "",
    val runtime: Int = 0,
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val actors: List<Actor> = listOf(),
    val reviewCount: Int = 0,
    val isLiked: Boolean = false,
)