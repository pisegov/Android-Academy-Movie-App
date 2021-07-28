package com.myaxa.academycourse

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myaxa.academycourse.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {

    //    private var movieCard: ConstraintLayout? = null
    private var recycler: RecyclerView? = null
    private lateinit var adapter: MoviesAdapter
    private var cardClickListener: OnMovieClicked? = null

    companion object {
        fun newInstance() = FragmentMoviesList()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.rv_movies_list)
        adapter = MoviesAdapter(cardClickListener)
        recycler?.adapter = adapter
        recycler?.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
//        recycler?.layoutManager = LinearLayoutManager(requireContext())
//        movieCard = view.findViewById<ConstraintLayout>(R.id.movie_card)
//        movieCard?.setOnClickListener {
//            cardClickListener?.goToDetailsPage()
//        }
    }

    override fun onStart() {
        super.onStart()

        updateData()
    }

    private fun updateData() {
        (recycler?.adapter as? MoviesAdapter)?.apply {
            bindItems(MoviesDataSource().getMovies())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnMovieClicked) {
            cardClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        cardClickListener = null
        recycler = null
    }
}

interface OnMovieClicked {
    fun goToDetailsPage(movieId: Int)
}