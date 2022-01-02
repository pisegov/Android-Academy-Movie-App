package com.myaxa.academycourse.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myaxa.academycourse.MovieRepositoryProvider
import com.myaxa.academycourse.R
import com.myaxa.academycourse.model.Actor
import com.myaxa.academycourse.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MOVIE_ID = "movieId"

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {
    private lateinit var viewModel: MovieDetailsViewModel

    private var actorsListRecycler: RecyclerView? = null
    private lateinit var actorsListAdapter: ActorsAdapter
    private var title: TextView? = null
    private var description: TextView? = null
    private var backgroundImage: ImageView? = null
    private var castTextView: TextView? = null
    private var reviewsNumber: TextView? = null
    private var genres: TextView? = null
    private var pg: TextView? = null
    private var starsList: MutableList<ImageView?> = mutableListOf()
    private var backButton: LinearLayout? = null
    private var backListener: OnBackButtonListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnBackButtonListener)
            backListener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)

        // getting the movie id from arguments list and creating view model with id
        val movieId = arguments?.getInt(MOVIE_ID) ?: 0

        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(
                (requireActivity() as MovieRepositoryProvider).provideMovieRepository(),
                movieId
            )
        )
            .get(MovieDetailsViewModel::class.java)

        // subscribing at movie live data
        viewModel.movieDetails.observe(this.viewLifecycleOwner) { movieData ->
            lifecycleScope.launch {
                movieData?.let { setMovie(it) } ?: showMovieNotFoundError()
            }
        }
        viewModel.errorMessage.observe(this.viewLifecycleOwner) { errorMessage ->
            showMovieNotFoundError(errorMessage)
        }
    }

    private suspend fun setMovie(movieDetails: MovieDetails) = withContext(Dispatchers.Main) {
        Glide.with(context)
            .load(movieDetails.detailImageUrl)
            .into(backgroundImage)

        title?.text = movieDetails.title
        description?.text = movieDetails.storyLine
        reviewsNumber?.text = movieDetails.reviewCount.toString() + " Reviews"
        pg?.text = "${movieDetails.pgAge}+"
        val rating = movieDetails.rating

        genres?.text = movieDetails.genres

        starsList.forEachIndexed { index, star ->
            val colorId = if (rating > index) R.color.pink else R.color.dark_grey
            star?.setColorFilter(requireContext().getColor(colorId))
        }

        if (movieDetails.actors.isEmpty()) {
            castTextView?.visibility = View.GONE
            actorsListRecycler?.visibility = View.GONE
        } else {
            bindActors(movieDetails.actors)
        }
    }

    private suspend fun bindActors(actors: List<Actor>) = withContext(Dispatchers.Main) {
        // binding actors list to recycler view's adapter
        (actorsListRecycler?.adapter as? ActorsAdapter)?.apply {
            bindItems(actors)
        }
    }

    private fun showMovieNotFoundError(text: String = "Error, movie not found") {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupViews(view: View) {
        actorsListAdapter = ActorsAdapter()
        actorsListAdapter.setHasStableIds(true)

        actorsListRecycler = view.findViewById(R.id.rv_cast)
        actorsListRecycler?.adapter = actorsListAdapter
        actorsListRecycler?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        backgroundImage = view.findViewById(R.id.background_image)
        castTextView = view.findViewById(R.id.tv_cast)
        genres = view.findViewById(R.id.tv_genres)
        reviewsNumber = view.findViewById(R.id.tv_reviews_number)
        pg = view.findViewById(R.id.tv_pg)
        backButton = view.findViewById(R.id.button_back)

        backButton?.setOnClickListener {
            backListener?.onBackClicked()
        }

        starsList.apply {
            add(view.findViewById(R.id.star_1))
            add(view.findViewById(R.id.star_2))
            add(view.findViewById(R.id.star_3))
            add(view.findViewById(R.id.star_4))
            add(view.findViewById(R.id.star_5))
        }
    }

    private fun closeViews() {
        actorsListRecycler = null

        title = null
        description = null
        backgroundImage = null
        castTextView = null
        reviewsNumber = null
        genres = null
        pg = null
        backButton = null

        starsList.clear()
    }

    override fun onDestroyView() {
        closeViews()
        super.onDestroyView()
    }

    override fun onDetach() {
        backListener = null

        super.onDetach()
    }


    interface OnBackButtonListener {
        fun onBackClicked()
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }
}