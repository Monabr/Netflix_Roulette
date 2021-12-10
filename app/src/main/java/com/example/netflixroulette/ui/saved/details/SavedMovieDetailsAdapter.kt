package com.example.netflixroulette.ui.saved.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.netflixroulette.R
import com.example.netflixroulette.databinding.ItemDetailsBinding
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.ui.searchWith.IMAGE_BASE
import java.text.SimpleDateFormat

class SavedMovieDetailsAdapter(
    var movies: List<MovieDB>,
    var callBackAdapterListener: CallBackAdapterListener
) : RecyclerView.Adapter<SavedMovieDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    inner class ViewHolder(private val binding: ItemDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDB) {

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
                .into(binding.itemDetailsIvPoster)

            binding.itemDetailsTvTitle.text = movie.title
            binding.itemDetailsTvDirector.text = movie.director
            binding.itemDetailsTvRating.text = movie.vote_average.toString() + "/10"

            if(!movie.release_date.isNullOrBlank())  {
                val parser =  SimpleDateFormat("yyyy-MM-dd")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val formattedDate = formatter.format(parser.parse(movie.release_date))
                binding.itemDetailsTvRelease.text = formattedDate
            } else {
                binding.itemDetailsTvRelease.text =
                    itemView.context.getText(R.string.MovieAdapter_Unknown_date)
            }

            binding.itemDetailsTvSummary.text = movie.overview

            binding.itemDetailsIbBack.setOnClickListener {
                callBackAdapterListener.onAdapterItemBackArrowPressed()
            }

            binding.itemDetailsIbSave.setOnClickListener {
                callBackAdapterListener.onAdapterItemSavePressed(movie)
            }

        }
    }

    interface CallBackAdapterListener {
        fun onAdapterItemBackArrowPressed()
        fun onAdapterItemSavePressed(movie: MovieDB)
    }
}