package com.myaxa.academycourse.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorResponse(
    val id: Int = 0,
    val name: String = "",
    @SerialName("profile_path")
    val profilePicture: String = "",
)