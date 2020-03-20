package com.example.netflixroulette.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.netflixroulette.R
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.views.SearchedMovieDetailsFragment
import com.example.netflixroulette.views.support_views.MainContainerActivity
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SavedMovieAdapter(
    var movies: List<MovieDB>
) : RecyclerView.Adapter<SavedMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(movies[position], position)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MovieDB, position: Int) {

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
                .apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                }

            Glide
                .with(itemView.context)
                .load(IMAGE_BASE + movie.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(circularProgressDrawable)
                .fallback(R.drawable.no_image_poster)
                .error(R.drawable.no_image_poster)
                .into(itemView.item_movie_iv_poster)

            itemView.item_movie_tv_category.text = movie.genre.toUpperCase(Locale.ROOT)
            itemView.item_movie_tv_director.text = movie.director
            itemView.item_movie_tv_name.text = movie.title
            itemView.item_movie_tv_rating.text =
                (movie.vote_average.toString() + R.string.Movie_max_rate)

            if (!movie.release_date.isNullOrBlank()) {
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val formattedDate = formatter.format(parser.parse(movie.release_date))
                itemView.item_movie_tv_release_date.text = formattedDate
            } else {
                itemView.item_movie_tv_release_date.text =
                    itemView.context.getText(R.string.MovieAdapter_Unknown_date)
            }

            if (!itemView.hasOnClickListeners()) {
                itemView.setOnClickListener {
                    (itemView.context as MainContainerActivity).findNavController(R.id.container)
                        .navigate(R.id.savedMovieDetailsFragment, Bundle().apply {
                            putParcelableArrayList(
                                SearchedMovieDetailsFragment.MOVIES,
                                ArrayList<MovieDB>(movies)
                            )
                            putInt(SearchedMovieDetailsFragment.CURRENT_ITEM, position)
                        })
                }
            }
        }
    }
}