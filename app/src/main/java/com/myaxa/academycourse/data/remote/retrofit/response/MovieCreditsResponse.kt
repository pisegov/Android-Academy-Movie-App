package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieCreditsResponse(
    @SerialName("cast")
    val cast: List<ActorResponse> = listOf(),
)