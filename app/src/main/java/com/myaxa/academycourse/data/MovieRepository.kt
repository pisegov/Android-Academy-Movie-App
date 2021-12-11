package com.myaxa.academycourse.data

import com.myaxa.academycourse.data.remote.retrofit.RetrofitModule
import com.myaxa.academycourse.data.remote.retrofit.response.ImageResponse
import com.myaxa.academycourse.model.Actor
import com.myaxa.academycourse.model.Genre
import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}

@ExperimentalSerializationApi
class RetrofitMovieRepository : MovieRepository {
    private var movies: List<Movie>? = null
    private lateinit var imagesApiUrls: ImageResponse
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            loadImagesConfig()
        }
    }

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val cachedMovies = movies
        if (cachedMovies != null) {
            cachedMovies
        } else {
            val moviesFromSource = loadMoviesFromSource()
            movies = moviesFromSource
            moviesFromSource
        }
    }

    private suspend fun loadMoviesFromSource(): List<Movie> = withContext(Dispatchers.IO) {
        val genres = RetrofitModule.moviesApi.getGenres().genres
        val data = RetrofitModule.moviesApi.getMovies().results

        return@withContext data.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                storyLine = movie.overview,
                imageUrl = imagesApiUrls.secureBaseUrl + imagesApiUrls.posterSizes[imagesApiUrls.posterSizes.size - 2] + movie.posterPicture,
                rating = (movie.ratings / 2).toInt(),
                reviewCount = movie.votesCount,
                pgAge = if (movie.adult) 16 else 13,
                runningTime = movie.runtime,
                genres = genres
                    .filter { genreResponse -> movie.genreIds.contains(genreResponse.id) }
                    .map { genreResponse -> Genre(genreResponse.id, genreResponse.name) },
                isLiked = false
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val movie = RetrofitModule.moviesApi.getMovie(movieId)

        return MovieDetails(
            actors = loadActors(movieId),
            id = movie.id,
            title = movie.title,
            storyLine = movie.overview,
            pgAge = if (movie.adult) 16 else 13,
            genres = movie.genres.map { genre -> Genre(genre.id, genre.name) },
            reviewCount = movie.voteCount,
            rating = (movie.voteAverage / 2).toInt(),
            isLiked = false,
            detailImageUrl = imagesApiUrls.secureBaseUrl
                    + imagesApiUrls.posterSizes[imagesApiUrls.posterSizes.size - 2]
                    + movie.backdropPath,
        )
    }

    private suspend fun loadActors(movieId: Int): List<Actor> = withContext(Dispatchers.IO) {
        RetrofitModule.moviesApi.getMovieCast(movieId).cast.map { actorResponse ->
            Actor(
                id = actorResponse.id,
                name = actorResponse.name,
                imageUrl = if (actorResponse.profilePicture != "") {
                    imagesApiUrls.secureBaseUrl + imagesApiUrls.profileSizes[imagesApiUrls.profileSizes.size - 1] + actorResponse.profilePicture
                } else ""
            )
        }
    }

    private suspend fun loadImagesConfig() = withContext(Dispatchers.IO) {
        imagesApiUrls = RetrofitModule.moviesApi.getApiConfiguration().images
    }
}
