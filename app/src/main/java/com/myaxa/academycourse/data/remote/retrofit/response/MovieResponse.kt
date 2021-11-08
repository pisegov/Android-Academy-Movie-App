package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieResponse(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("poster_path")
    val posterPicture: String = "",
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("genre_ids")
    val genreIds: List<Int> = listOf(),
    @SerialName("vote_average")
    val ratings: Float = 0.0F,
    @SerialName("vote_count")
    val votesCount: Int = 0,
    @SerialName("overview")
    val overview: String = "",
    @SerialName("adult")
    val adult: Boolean = false,
)