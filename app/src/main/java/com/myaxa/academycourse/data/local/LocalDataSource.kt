package com.myaxa.academycourse.data.local

import com.myaxa.academycourse.data.local.movies.MovieEntity
import com.myaxa.academycourse.model.Movie

class LocalDataSource(private val database: AppDatabase) {
    suspend fun getMovies(): List<Movie> =
        database.moviesDao.getAll().map { movieEntity -> movieEntity.toMovie() }

    suspend fun insertMovies(moviesList: List<Movie>) =
        database.moviesDao.insertAll(moviesList.map { movie -> movie.toMovieEntity() })

    private fun MovieEntity.toMovie() = Movie(
        id = id,
        title = title,
        storyLine = storyLine,
        imageUrl = imageUrl,
        pgAge = pgAge,
        genres = genres,
        runningTime = runningTime,
        reviewCount = reviewCount,
        isLiked = isLiked,
        rating = rating
    )

    private fun Movie.toMovieEntity() = MovieEntity(
        id = id,
        title = title,
        storyLine = storyLine,
        imageUrl = imageUrl,
        pgAge = pgAge,
        genres = genres,
        runningTime = runningTime,
        reviewCount = reviewCount,
        isLiked = isLiked,
        rating = rating
    )
}