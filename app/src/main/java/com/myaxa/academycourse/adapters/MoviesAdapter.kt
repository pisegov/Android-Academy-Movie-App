package com.myaxa.academycourse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.myaxa.academycourse.R
import com.myaxa.academycourse.fragments.OnMovieClicked
import com.myaxa.academycourse.model.Movie

class MoviesAdapter(private val clickListener: OnMovieClicked?) :
        RecyclerView.Adapter<MoviesViewHolder>() {

    private var movies = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.onBind(movies[position])
        holder.itemView.setOnClickListener {
            clickListener?.goToDetailsPage(movies[position].id)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun bindItems(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

abstract class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MoviesViewHolder(itemView: View) : DataViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById<ImageView>(R.id.iv_movie_card_image)
    private val title: TextView = itemView.findViewById<TextView>(R.id.title_in_list)

    fun onBind(movie: Movie) {
        Glide.with(context)
                .load(movie.imageUrl)
                .apply(imageOption)
                .into(image)

        title.text = movie.title
    }


    companion object {
        private val imageOption = RequestOptions()
                .placeholder(R.drawable.movie_in_list)
                .fallback(R.drawable.movie_in_list)
                .transform(RoundedCorners(20))
    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context

