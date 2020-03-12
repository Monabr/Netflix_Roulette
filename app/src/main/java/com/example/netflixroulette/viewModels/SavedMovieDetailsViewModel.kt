package com.example.netflixroulette.viewModels

import android.database.sqlite.SQLiteConstraintException
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SavedMovieDetailsViewModel @Inject constructor(
    private val themoviedbRepository: ThemoviedbRepository
) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun handleActionSave(view: View, movie: MovieDB) = launch {
        try {
            themoviedbRepository.insertMovie(movie)
            withContext(Dispatchers.Main) {
                Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException) {
            themoviedbRepository.deleteMovie(movie)
            withContext(Dispatchers.Main) {
                Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
