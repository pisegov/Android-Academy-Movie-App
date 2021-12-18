package com.myaxa.academycourse.data

import com.myaxa.academycourse.data.remote.retrofit.NetworkDataSource
import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieRepositoryImpl : MovieRepository {
    private val networkDataSource = NetworkDataSource()

    override suspend fun loadMovies(): List<Movie> {
        return networkDataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        return networkDataSource.loadMovie(movieId)
    }
}