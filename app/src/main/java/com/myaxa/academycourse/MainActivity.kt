package com.myaxa.academycourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myaxa.academycourse.data.JsonMovieRepository
import com.myaxa.academycourse.data.MovieRepository
import com.myaxa.academycourse.moviedetails.FragmentMoviesDetails
import com.myaxa.academycourse.movies.FragmentMoviesList
import com.myaxa.academycourse.movies.OnMovieClicked

class MainActivity : AppCompatActivity(),
    OnMovieClicked,
    MovieRepositoryProvider {

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MoviesList"
        const val MOVIES_DETAILS_FRAGMENT_TAG = "MoviesDetails"
    }

    private val jsonMovieRepository = JsonMovieRepository(this)
    private var moviesListFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun goToDetailsPage(movieId: Int) {
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

    override fun provideMovieRepository(): MovieRepository = jsonMovieRepository
}