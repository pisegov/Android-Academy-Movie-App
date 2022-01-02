package com.myaxa.academycourse.data

import com.myaxa.academycourse.data.local.LocalDataSource
import com.myaxa.academycourse.data.remote.retrofit.NetworkDataSource
import com.myaxa.academycourse.data.remote.retrofit.NetworkResult
import com.myaxa.academycourse.model.Movie
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalSerializationApi
class MovieRepositoryImpl(
    private val localDataSource: LocalDataSource
) :
    MovieRepository {

    private val networkDataSource = NetworkDataSource()

    override suspend fun loadMovies(): NetworkResult<List<Movie>> {
        return safeApiCall { networkDataSource.loadMovies() }
    }

    override suspend fun loadMovie(movieId: Int): NetworkResult<MovieDetails> {
        return safeApiCall { networkDataSource.loadMovie(movieId) }
    }

    override suspend fun addMovies(moviesList: List<Movie>) {
        localDataSource.insertMovies(moviesList)
    }

    override suspend fun getLocalMovies(): List<Movie> =
        localDataSource.getMovies()

//    override suspend fun getLocalMovieDetails(): MovieDetails {
//        TODO("Not yet implemented")
//    }

    override suspend fun loadNetworkConfig(): NetworkResult<Unit> {
        return safeApiCall { networkDataSource.loadImagesConfig() }
    }

    private suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (error: Throwable) {
                when (error) {
                    is IOException ->
                        NetworkResult.NetworkError
                    is HttpException -> {
                        val response = error.response()?.errorBody()?.source().toString()
                        NetworkResult.GenericError(response)
                    }
                    else -> NetworkResult.GenericError("Undefined error")
                }
            }
        }
    }

}