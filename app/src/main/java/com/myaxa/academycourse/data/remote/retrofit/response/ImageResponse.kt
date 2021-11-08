package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String> = listOf(),
    @SerialName("base_url")
    val baseUrl: String = "",
    @SerialName("logo_sizes")
    val logoSizes: List<String> = listOf(),
    @SerialName("poster_sizes")
    val posterSizes: List<String> = listOf(),
    @SerialName("profile_sizes")
    val profileSizes: List<String> = listOf(),
    @SerialName("secure_base_url")
    val secureBaseUrl: String = "",
    @SerialName("still_sizes")
    val stillSizes: List<String> = listOf(),
)