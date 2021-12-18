package com.myaxa.academycourse.data

import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}
