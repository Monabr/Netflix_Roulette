package com.example.netflixroulette.viewModels

import android.database.sqlite.SQLiteConstraintException
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailsViewModel @Inject constructor(
    private val themoviedbRepository: ThemoviedbRepository
) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun handleActionSave(view: View, movie: Movie) = launch {
        val movieDB = mapToModelDB(movie)
        try {
            themoviedbRepository.insertMovie(movieDB)
            withContext(Dispatchers.Main) {
                Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException) {
            themoviedbRepository.deleteMovie(movieDB)
            withContext(Dispatchers.Main) {
                Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun mapToModelDB(movie: Movie): MovieDB {
         return MovieDB(
             movie.popularity,
             movie.id,
             movie.vote_count,
             movie.vote_average,
             movie.title,
             movie.release_date,
             movie.original_language,
             movie.original_title,
             movie.genre_ids,
             movie.backdrop_path,
             movie.adult,
             movie.overview,
             movie.poster_path,
             movie.genre,
             movie.director
         )
    }
}
