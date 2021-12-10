package com.example.netflixroulette.ui.searchWith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.netflixroulette.R
import com.example.netflixroulette.databinding.ItemMovieBinding
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.ui.MainContainerActivity
import com.example.netflixroulette.ui.searchWith.details.SearchedMovieDetailsFragment
import java.text.SimpleDateFormat

const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"

class SearchedMovieAdapter(
    var movies: List<Movie>
) : RecyclerView.Adapter<SearchedMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(movies[position], position)

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, position: Int) {

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
                .apply {
                    this.strokeWidth = 5f
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
                .into(binding.itemMovieIvPoster)

            binding.itemMovieTvCategory.text = movie.genre.uppercase()
            binding.itemMovieTvDirector.text = movie.director
            binding.itemMovieTvName.text = movie.title
            binding.itemMovieTvRating.text = (movie.vote_average.toString() + "/10")

            if (!movie.release_date.isNullOrBlank()) {
                val parser = SimpleDateFormat("yyyy-MM-dd")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val formattedDate = formatter.format(parser.parse(movie.release_date))
                binding.itemMovieTvReleaseDate.text = formattedDate
            } else {
                binding.itemMovieTvReleaseDate.text =
                    itemView.context.getText(R.string.MovieAdapter_Unknown_date)
            }

            if (!itemView.hasOnClickListeners()) {
                itemView.setOnClickListener {
                    (itemView.context as MainContainerActivity).findNavController(R.id.container)
                        .navigate(R.id.detailsFragment, Bundle().apply {
                            val arr = ArrayList<Movie>(movies)
                            putParcelableArrayList(SearchedMovieDetailsFragment.MOVIES, arr)
                            putInt(SearchedMovieDetailsFragment.CURRENT_ITEM, position)
                        })
                }
            }
        }
    }
}