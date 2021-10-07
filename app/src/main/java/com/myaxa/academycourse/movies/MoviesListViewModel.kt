package com.myaxa.academycourse.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repository: MovieRepository) : ViewModel() {
    private var _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        viewModelScope.launch {
            loadMovies()
        }
    }

    private suspend fun loadMovies() {

        // made through async-await to practice different methods with coroutines
        val differed = viewModelScope.async {
            repository.loadMovies()
        }

        _movies.value = differed.await()
    }
}