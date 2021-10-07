package com.myaxa.academycourse.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.model.Movie
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    private val movieId: Int
) :
    ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie?> = _movie

    init {
        loadDetails()
    }

    // public because its interesting to make update functionality
    fun loadDetails() {
        viewModelScope.launch {
            _movie.value = repository.loadMovie(movieId)
        }
    }
}