package com.myaxa.academycourse.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import com.myaxa.academycourse.model.Movie
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

//    private val viewModel: MovieDetailsViewModel by viewModels {
//        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
//    }

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
        viewModel.movie.observe(this.viewLifecycleOwner) { movieData ->
            lifecycleScope.launch {
                movieData?.let { setMovie(it) } ?: showMovieNotFoundError()
            }
        }
    }

    private suspend fun setMovie(movie: Movie) = withContext(Dispatchers.Main) {
        Glide.with(context)
            .load(movie.detailImageUrl)
            .into(backgroundImage)

        title?.text = movie.title
        description?.text = movie.storyLine
        reviewsNumber?.text = movie.reviewCount.toString() + " Reviews"
        pg?.text = "${movie.pgAge}+"

        var genresString: String = ""
        movie.genres.forEach { genre ->
            genresString += "${genre.name}, "
        }
        genres?.text = genresString.removeSuffix(", ")

        starsList.forEachIndexed { index, star ->
            val colorId = if (movie.rating > index) R.color.pink else R.color.dark_grey
            star?.setColorFilter(requireContext().getColor(colorId))
        }

        if (movie.actors.isEmpty()) {
            castTextView?.visibility = View.GONE
            actorsListRecycler?.visibility = View.GONE
        } else {
            bindActors(movie)
        }
    }

    private suspend fun bindActors(movie: Movie) = withContext(Dispatchers.Main) {
        // binding actors list to recycler view's adapter
        (actorsListRecycler?.adapter as? ActorsAdapter)?.apply {
            bindItems(movie.actors)
        }
    }

    private fun showMovieNotFoundError() {
        Toast.makeText(
            requireContext(),
            "Error, movie not found",
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

        starsList.clear()
    }

    override fun onDestroyView() {
        closeViews()
        super.onDestroyView()
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