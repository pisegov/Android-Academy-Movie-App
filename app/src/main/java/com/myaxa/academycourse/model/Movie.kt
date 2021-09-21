package com.myaxa.academycourse.model

data class Movie(
        val id: Int,
        val title: String,
        val storyLine: String,
        val imageUrl: String,
        val actors: List<Actor>,

        val pgAge: Int,
        val genres: List<Genre>,
        val runningTime: Int,
        val reviewCount: Int,
        val isLiked: Boolean,
        val rating: Int,
        val detailImageUrl: String,
)