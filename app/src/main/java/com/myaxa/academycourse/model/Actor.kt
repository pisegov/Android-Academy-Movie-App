package com.myaxa.academycourse.model

import java.io.Serializable

data class Actor(
        val id: Int = 0,
        val name: String = "",
        val imageUrl: String = "",
) : Serializable