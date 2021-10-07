package com.myaxa.academycourse

import com.myaxa.academycourse.data.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}