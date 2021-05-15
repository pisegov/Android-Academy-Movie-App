package com.myaxa.academycourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), CardClickListener {

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MoviesList"
        const val MOVIES_DETAILS_FRAGMENT_TAG = "MoviesDetails"
    }

    private var moviesListFragment: FragmentMoviesList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            moviesListFragment = FragmentMoviesList() //TODO make newInstance(args) method
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

    override fun goToDetailsPage() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_frame_layout, FragmentMoviesDetails(), MOVIES_DETAILS_FRAGMENT_TAG)
            addToBackStack(MOVIES_DETAILS_FRAGMENT_TAG)
            commit()
        }
    }

}