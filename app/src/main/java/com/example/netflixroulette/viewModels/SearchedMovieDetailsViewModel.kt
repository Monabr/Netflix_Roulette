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

class SearchedMovieDetailsViewModel @Inject constructor(
    private val themoviedbRepository: ThemoviedbRepository
) : ViewModel(), CoroutineScope {

    /**
     * Job for [coroutineContext]
     *
     */
    private val job = SupervisorJob()

    /**
     * Using for set default dispatcher so you no need to create yours for [launch] something.
     *
     * If you want to cancel all coroutines that started here use [cancelChildren].
     * For example coroutineContext.cancelChildren()
     */
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    /**
     * Save or delete movie depends if it's already in database and makes notification about it
     *
     * @param view for [Snackbar.make] notification
     * @param movie that we want save or delete
     */
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

    /**
     * Convert [Movie] object to [MovieDB] object because we need different objects for database because of Clean Architecture
     *
     * @param movie object to convert
     * @return ready object for database
     */
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
