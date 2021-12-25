package com.myaxa.academycourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.data.MovieRepositoryImpl
import com.myaxa.academycourse.data.local.AppDatabase
import com.myaxa.academycourse.data.local.LocalDataSource
import com.myaxa.academycourse.moviedetails.FragmentMoviesDetails
import com.myaxa.academycourse.movies.FragmentMoviesList
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity(),
    FragmentMoviesList.OnMovieListener,
    FragmentMoviesDetails.OnBackButtonListener,
    MovieRepositoryProvider {

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MoviesList"
        const val MOVIES_DETAILS_FRAGMENT_TAG = "MoviesDetails"
    }

    private lateinit var movieRepository: MovieRepository
    private var moviesListFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieRepository = MovieRepositoryImpl(
            localDataSource = LocalDataSource(
                AppDatabase.createInstance(applicationContext)
            )
        )
        goToListPage(savedInstanceState)
    }

    override fun onMovieClicked(movieId: Int) {
        goToMovieDetailsPage(movieId)
    }

    override fun onBackClicked() {
        goBack()
    }

    private fun goToListPage(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            moviesListFragment = FragmentMoviesList.newInstance()
            moviesListFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_frame_layout, this, MOVIES_LIST_FRAGMENT_TAG)
                    .commit()
            }
        } else {
            moviesListFragment =
                supportFragmentManager
                    .findFragmentByTag(MOVIES_LIST_FRAGMENT_TAG) as? FragmentMoviesList
        }
    }

    private fun goToMovieDetailsPage(movieId: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.main_frame_layout,
                FragmentMoviesDetails.newInstance(movieId),
                MOVIES_DETAILS_FRAGMENT_TAG
            )
            addToBackStack(MOVIES_DETAILS_FRAGMENT_TAG)
            commit()
        }
    }

    private fun goBack() {
        supportFragmentManager.popBackStack()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository
}