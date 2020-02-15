package com.example.netflixroulette.viewModels

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.Movie
import com.example.netflixroulette.network.ThemoviedbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SavedMoviesViewModel @Inject constructor(
    private val themoviedbRepository: ThemoviedbRepository
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var scrollPosition: Parcelable? = null

    fun onActivityCreated() {
        launch {
            movies.postValue(themoviedbRepository.getAllMovies())
        }
    }
}
