package com.example.netflixroulette.ui.saved.details

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.repository.ThemoviedbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SavedMovieDetailsViewModel @Inject constructor(
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
     * Use to observe the save status of movies
     *
     */
    var isSaved: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Save or delete movie depends if it's already in database and makes notification about it
     *
     * @param movie that we want save or delete
     */
    fun handleActionSave(movie: MovieDB) = launch {
        try {
            themoviedbRepository.insertMovie(movie)
            isSaved.postValue(true)

        } catch (e: SQLiteConstraintException) {
            themoviedbRepository.deleteMovie(movie)
            isSaved.postValue(false)

        }
    }
}
