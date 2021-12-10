package com.example.netflixroulette.ui.saved

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SavedMoviesViewModel @Inject constructor(
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
     * List of movies that will be observed by fragment
     */
    var movies: MutableLiveData<List<MovieDB>> = MutableLiveData()

    /**
     * Saved position of items list
     */
    var scrollPosition: Parcelable? = null

    /**
     * Load and post value to [movies] when we enter to the fragment
     *
     */
    fun onActivityCreated() {
        launch {
            movies.postValue(themoviedbRepository.getAllMovies())
        }
    }
}
