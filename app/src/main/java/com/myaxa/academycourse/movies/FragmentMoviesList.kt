package com.myaxa.academycourse.movies

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myaxa.academycourse.MovieRepositoryProvider
import com.myaxa.academycourse.R

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var recycler: RecyclerView? = null
    private lateinit var adapter: MoviesAdapter
    private var cardClickListener: OnMovieListener? = null

    companion object {
        fun newInstance() = FragmentMoviesList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler(view)

        viewModel.movies.observe(this.viewLifecycleOwner) { moviesList ->
            (recycler?.adapter as? MoviesAdapter)?.apply {
                bindItems(moviesList)
            }
        }
        val toast = Toast.makeText(context, "Loading...", Toast.LENGTH_LONG)
        viewModel.loadingState.observe(this.viewLifecycleOwner) { loading ->
            when (loading) {
                true -> {
                    toast.show()
                }
                else -> {
                    toast.cancel()
                }
            }
        }
        viewModel.errorMessage.observe(this.viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecycler(view: View) {
        adapter = MoviesAdapter(cardClickListener)
        adapter.setHasStableIds(true)

        recycler = view.findViewById(R.id.rv_movies_list)
        recycler?.adapter = adapter
        recycler?.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnMovieListener) {
            cardClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        cardClickListener = null
        recycler = null
    }

    interface OnMovieListener {
        fun onMovieClicked(movieId: Int)
    }
}

