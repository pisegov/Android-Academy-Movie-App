package com.myaxa.academycourse.movies

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
import com.myaxa.academycourse.model.Movie

class MoviesAdapter(private val clickListener: FragmentMoviesList.OnMovieListener?) :
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
            clickListener?.onMovieClicked(movies[position].id)
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
    private val pg: TextView = itemView.findViewById(R.id.tv_pg_movie_card)
    private val genres: TextView = itemView.findViewById(R.id.tv_genres_movie_card)
    private val reviewsNumber: TextView = itemView.findViewById(R.id.tv_reviews_movie_card)
    private val length: TextView = itemView.findViewById(R.id.tv_length_movie_card)
    private val starsList: List<ImageView> = listOf(
        itemView.findViewById(R.id.star_list_1),
        itemView.findViewById(R.id.star_list_2),
        itemView.findViewById(R.id.star_list_3),
        itemView.findViewById(R.id.star_list_4),
        itemView.findViewById(R.id.star_list_5),
    )

    fun onBind(movie: Movie) {
        Glide.with(context)
            .load(movie.imageUrl)
            .apply(imageOption)
            .into(image)

        title.text = movie.title

        pg.text = "${movie.pgAge}+"

        reviewsNumber.text = movie.reviewCount.toString() + " Reviews"

        length.text = "${movie.runningTime} MIN"

        var genresString: String = ""
        movie.genres.forEach { genre ->
            genresString += "${genre.name}, "
        }
        genres.text = genresString.removeSuffix(", ")

        starsList.forEachIndexed { index, star ->
            val colorId = if (movie.rating > index) R.color.pink else R.color.dark_grey
            star.setColorFilter(context.getColor(colorId))
        }

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

