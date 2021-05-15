package com.myaxa.academycourse

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {

    private var movieCard: ConstraintLayout? = null
    private var cardClickListener: CardClickListener? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieCard = view.findViewById<ConstraintLayout>(R.id.movie_card)
        movieCard?.setOnClickListener {
            cardClickListener?.goToDetailsPage()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CardClickListener) {
            cardClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()

        cardClickListener = null
    }
}

interface CardClickListener {
    fun goToDetailsPage()
}