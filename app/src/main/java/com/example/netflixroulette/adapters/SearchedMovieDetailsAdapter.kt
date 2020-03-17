package com.example.netflixroulette.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.netflixroulette.R
import com.example.netflixroulette.models.json.jsonModels.Movie
import kotlinx.android.synthetic.main.item_details.view.*
import java.text.SimpleDateFormat


class SearchedMovieDetailsAdapter(
    var movies: List<Movie>,
    var callBackAdapterListener: CallBackAdapterListener
) : RecyclerView.Adapter<SearchedMovieDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_details,
            parent,
            false
        )
    )

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {

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
                .into(itemView.item_details_iv_poster)

            itemView.item_details_tv_title.text = movie.title
            itemView.item_details_tv_director.text = movie.director
            itemView.item_details_tv_rating.text = movie.vote_average.toString() + "/10"

            if(!movie.release_date.isNullOrBlank())  {
                val parser =  SimpleDateFormat("yyyy-MM-dd")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val formattedDate = formatter.format(parser.parse(movie.release_date))
                itemView.item_details_tv_release.text = formattedDate
            } else {
                itemView.item_details_tv_release.text =
                    itemView.context.getText(R.string.MovieAdapter_Unknown_date)
            }

            itemView.item_details_tv_summary.text = movie.overview

            itemView.item_details_ib_back.setOnClickListener {
                callBackAdapterListener.onAdapterItemBackArrowPressed()
            }

            itemView.item_details_ib_save.setOnClickListener {
                callBackAdapterListener.onAdapterItemSavePressed(movie)
            }

        }
    }

    interface CallBackAdapterListener {
        fun onAdapterItemBackArrowPressed()
        fun onAdapterItemSavePressed(movie: Movie)
    }
}