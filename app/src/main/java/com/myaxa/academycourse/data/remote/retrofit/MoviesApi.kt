package com.myaxa.academycourse.data.remote.retrofit

import com.myaxa.academycourse.data.remote.retrofit.response.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
    @GET("configuration")
    suspend fun getApiConfiguration(): ImagesConfigurationResponse

    @GET("movie/now_playing")
    suspend fun getMovies(): MoviesListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
    ): MovieCreditsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresListResponse
}