package com.myaxa.academycourse.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myaxa.academycourse.R
import com.myaxa.academycourse.adapters.ActorsAdapter
import com.myaxa.academycourse.data.JsonMovieRepository
import com.myaxa.academycourse.model.Movie
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MOVIE_ID = "movieId"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {
    // TODO: Rename and change types of parameters
    private var movieId: Int? = null
    private var actorsRecycler: RecyclerView? = null
    private lateinit var adapter: ActorsAdapter
    private var title: TextView? = null
    private var description: TextView? = null
    private var movie: Movie? = null
    private var backgroundImage: ImageView? = null
    private var castTextView: TextView? = null
    private var reviewsNumber: TextView? = null
    private var genres: TextView? = null
    private var pg: TextView? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActorsAdapter()
        adapter.setHasStableIds(true)
        setupViews(view)

        coroutineScope.launch {
            movie = withContext(Dispatchers.IO) {
                JsonMovieRepository(requireContext()).loadMovie(movieId!!)
            }
            withContext(Dispatchers.Main) {
                Glide.with(context)
                        .load(movie?.detailImageUrl)
                        .into(backgroundImage)

                title?.text = movie?.title
                description?.text = movie?.storyLine
                reviewsNumber?.text = movie?.reviewCount.toString() + " Reviews"
                pg?.text = "${movie?.pgAge}+"

                var genresString: String = ""
                movie?.genres?.forEach { genre ->
                    genresString += "${genre.name}, "
                }
                genres?.text = genresString.removeSuffix(", ")

                val starsList: List<ImageView> = listOf(
                        view.findViewById(R.id.star_1),
                        view.findViewById(R.id.star_2),
                        view.findViewById(R.id.star_3),
                        view.findViewById(R.id.star_4),
                        view.findViewById(R.id.star_5),
                )

                val rating = movie?.rating ?: 0

                for (i in 0 until rating) {
                    starsList[i].setColorFilter(requireContext().getColor(R.color.pink))
                }
                for (i in rating..starsList.lastIndex) {
                    starsList[i].setColorFilter(requireContext().getColor(R.color.dark_grey))
                }

                if (movie?.actors?.size == 0) {
                    castTextView?.visibility = View.GONE
                    actorsRecycler?.visibility = View.GONE
                } else {
                    bindActors()
                }
            }
        }

    }

    private suspend fun bindActors() = withContext(Dispatchers.Main) {

        (actorsRecycler?.adapter as? ActorsAdapter)?.apply {
            bindItems(movie?.actors)
        }
    }

    override fun onDetach() {
        super.onDetach()
        closeViews()
    }

    private fun setupViews(view: View) {
        actorsRecycler = view.findViewById(R.id.rv_cast)
        actorsRecycler?.adapter = adapter
        actorsRecycler?.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                GridLayoutManager(requireContext(), 4, LinearLayoutManager.HORIZONTAL, false)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.description)
        backgroundImage = view.findViewById(R.id.background_image)
        castTextView = view.findViewById(R.id.tv_cast)
        genres = view.findViewById(R.id.tv_genres)
        reviewsNumber = view.findViewById(R.id.tv_reviews_number)
        pg = view.findViewById(R.id.tv_pg)
    }

    private fun closeViews() {
        actorsRecycler = null

        title = null
        description = null
        backgroundImage = null
        castTextView = null
        reviewsNumber = null
        genres = null
        pg = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMoviesDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movieId: Int) =
                FragmentMoviesDetails().apply {
                    arguments = Bundle().apply {
                        putInt(MOVIE_ID, movieId)
                    }
                }
    }
}