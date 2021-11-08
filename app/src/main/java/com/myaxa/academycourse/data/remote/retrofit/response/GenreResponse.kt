package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
)