package com.myaxa.academycourse.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.data.remote.retrofit.NetworkResult
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    private val movieId: Int,
) :
    ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    private val _errorMessage = MutableLiveData<String>()
    private val _loadingState = MutableLiveData<Boolean>(false)

    val movieDetails: LiveData<MovieDetails?> = _movieDetails
    val errorMessage: LiveData<String> = _errorMessage
    val loadingState: LiveData<Boolean> = _loadingState

    init {
        viewModelScope.launch {
            loadDetails()
        }

    }

    // public because its interesting to make update functionality
    suspend fun loadDetails() {
        _loadingState.value = true

        when (val detailsResponse = repository.loadMovie(movieId)) {
            is NetworkResult.Success -> {
                _movieDetails.value = detailsResponse.data
            }
            is NetworkResult.GenericError -> {
                _errorMessage.value = detailsResponse.message
            }
            is NetworkResult.NetworkError -> {
                _errorMessage.value = "Network error"
            }
        }
        _loadingState.value = false
    }
}