package com.myaxa.academycourse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myaxa.academycourse.R
import com.myaxa.academycourse.adapters.ActorsAdapter
import com.myaxa.academycourse.data.models.Movie
import com.myaxa.academycourse.domain.MoviesDataSource

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MOVIE_ID = "movieId"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMoviesDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var movieId: Int? = null
    private var actorsRecycler: RecyclerView? = null
    private lateinit var adapter: ActorsAdapter
    private var title: TextView? = null
    private var description: TextView? = null
    private var movie: Movie? = null
    private var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActorsAdapter()
        adapter.setHasStableIds(true)

        actorsRecycler = view.findViewById(R.id.rv_cast)
        actorsRecycler?.adapter = adapter
        actorsRecycler?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        movie = MoviesDataSource().getMovies()[movieId!!]
        title = view.findViewById(R.id.title)
        title?.text = movie?.title
        description = view.findViewById(R.id.description)
        description?.text = movie?.description
        image = view.findViewById(R.id.background_image)
        Glide.with(context)
                .load(movie?.image)
                .into(image)
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {

        (actorsRecycler?.adapter as? ActorsAdapter)?.apply {
            bindItems(movie?.cast)
        }
    }

    override fun onDetach() {
        super.onDetach()
        actorsRecycler = null
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