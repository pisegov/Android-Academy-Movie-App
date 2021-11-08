package com.myaxa.academycourse.data.remote.retrofit.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("genres")
    val genres: List<GenreResponse> = listOf(),
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("overview")
    val overview: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("revenue")
    val revenue: Int = 0,
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagline: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
)