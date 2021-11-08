package com.myaxa.academycourse.data.remote.retrofit.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesConfigurationResponse(
    @SerialName("images")
    val images: ImageResponse = ImageResponse(),
)

