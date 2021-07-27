package com.myaxa.academycourse.data.models

data class Movie(
    val title: String,
    val description: String,
    val image: String,
    val cast: List<Actor>
)