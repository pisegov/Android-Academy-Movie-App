package com.myaxa.academycourse.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.data.remote.retrofit.NetworkResult
import com.myaxa.academycourse.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListViewModel(private val repository: MovieRepository) : ViewModel() {
    private var _movies = MutableLiveData<List<Movie>>()
    private var _loadingState = MutableLiveData<Boolean>(false)
    private var _errorMessage = MutableLiveData<String>()

    val movies: LiveData<List<Movie>>
        get() = _movies
    val loadingState: LiveData<Boolean> = _loadingState
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            loadConfig()
            loadMovies()
        }
    }

    private suspend fun loadConfig() =
        when (val configResponse = repository.loadNetworkConfig()) {
            is NetworkResult.NetworkError -> {
                _errorMessage.value = "Network error"
            }
            is NetworkResult.GenericError -> {
                _errorMessage.value = configResponse.message
            }
            else -> {}
        }

    private suspend fun loadMovies() {
        _loadingState.value = true

        val localMovies = withContext(Dispatchers.IO) {
            repository.getLocalMovies()
        }

        if (localMovies.isNotEmpty()) {
            _movies.value = localMovies
        }

        _loadingState.value = false

        val remoteMoviesResult = withContext(Dispatchers.IO) {
            repository.loadMovies()
        }

        when (remoteMoviesResult) {
            is NetworkResult.Success -> {
                val newFilms = remoteMoviesResult.data

                // delete Previous Movies from db
                withContext(Dispatchers.IO) {
                    repository.addMovies(newFilms)
                }

                _movies.value = remoteMoviesResult.data
            }

            is NetworkResult.GenericError -> {
                _errorMessage.value = remoteMoviesResult.message
            }
            is NetworkResult.NetworkError -> {
                _errorMessage.value = "UndefinedError"
            }
        }
    }

//    private fun executeMethodWithLoading(method: suspend () -> List<Movie>) {
//        val isLoadingNow = loadingState.value ?: false
//        if (!isLoadingNow) {
//            viewModelScope.launch {
//                _loadingState.value = true
//
//                val updatedMovies = method()
//
//                _movies.value = updatedMovies
//
//                _loadingState.value = false
//            }
//        }
//    }

}