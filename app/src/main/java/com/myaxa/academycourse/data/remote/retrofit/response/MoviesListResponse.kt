package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.Serializable


@Serializable
data class MoviesListResponse(
    val results: List<MovieResponse> = listOf(),
)
