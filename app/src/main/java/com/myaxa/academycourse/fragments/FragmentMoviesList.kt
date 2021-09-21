package com.myaxa.academycourse.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myaxa.academycourse.R
import com.myaxa.academycourse.adapters.MoviesAdapter
import com.myaxa.academycourse.data.JsonMovieRepository
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {

    //    private var movieCard: ConstraintLayout? = null
    private var recycler: RecyclerView? = null
    private lateinit var adapter: MoviesAdapter
    private var cardClickListener: OnMovieClicked? = null
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)

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

        adapter = MoviesAdapter(cardClickListener)
        adapter.setHasStableIds(true)

        recycler = view.findViewById(R.id.rv_movies_list)
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

        coroutineScope.launch {
            updateData()
        }
    }

    private suspend fun updateData() = withContext(Dispatchers.Main) {
        val deferred = coroutineScope.async() {
            JsonMovieRepository(requireContext()).loadMovies()
        }

        (recycler?.adapter as? MoviesAdapter)?.apply {
//            bindItems(MoviesDataSource().getMovies())
            bindItems(deferred.await())
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