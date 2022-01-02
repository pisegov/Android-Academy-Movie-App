package com.myaxa.academycourse.data

import com.myaxa.academycourse.data.remote.retrofit.NetworkResult
import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails

interface MovieRepository {
    suspend fun loadNetworkConfig(): NetworkResult<Unit>
    suspend fun loadMovies(): NetworkResult<List<Movie>>
    suspend fun loadMovie(movieId: Int): NetworkResult<MovieDetails>
    suspend fun addMovies(moviesList: List<Movie>)
    suspend fun getLocalMovies(): List<Movie>
//    suspend fun getLocalMovieDetails(): MovieDetails
}
