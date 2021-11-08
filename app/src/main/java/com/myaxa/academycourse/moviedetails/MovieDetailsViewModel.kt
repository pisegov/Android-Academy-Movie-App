package com.myaxa.academycourse.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    private val movieId: Int,
) :
    ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails?> = _movieDetails

    init {
        loadDetails()
    }

    // public because its interesting to make update functionality
    fun loadDetails() {
        viewModelScope.launch {
            _movieDetails.value = repository.loadMovie(movieId)
        }
    }
}