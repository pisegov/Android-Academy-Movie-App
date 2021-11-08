package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GenresListResponse(
    @SerialName("genres")
    val genres: List<GenreResponse> = listOf(),
)
