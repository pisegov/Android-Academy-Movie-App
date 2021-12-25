package com.myaxa.academycourse.data.remote.retrofit

import com.myaxa.academycourse.data.remote.retrofit.response.ImageResponse
import com.myaxa.academycourse.model.Actor
import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class NetworkDataSource {
    private lateinit var imagesApiUrls: ImageResponse

    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
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
                    .joinToString(", ") { genre -> genre.name },
                isLiked = false
            )
        }
    }

    suspend fun loadMovie(movieId: Int): MovieDetails {
        val movie = RetrofitModule.moviesApi.getMovieDetails(movieId)

        return MovieDetails(
            actors = loadActors(movieId),
            id = movie.id,
            title = movie.title,
            storyLine = movie.overview,
            pgAge = if (movie.adult) 16 else 13,
            genres = movie.genres
                .joinToString(", ") { genre -> genre.name },
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

    suspend fun loadImagesConfig() = withContext(Dispatchers.IO) {
        imagesApiUrls = RetrofitModule.moviesApi.getApiConfiguration().images
    }
}
