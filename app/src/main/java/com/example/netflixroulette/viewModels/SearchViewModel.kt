package com.example.netflixroulette.viewModels

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.json.jsonModels.Genre
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.models.json.jsonModels.PersonCrew
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchViewModel @Inject constructor(
    private val themoviedbRepository: ThemoviedbRepository
) : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    lateinit var genresList: List<Genre>

    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var scrollPosition: Parcelable? = null

    init {
        launch {
            genresList = themoviedbRepository.getGenres()
        }
    }

    fun handleSearchByTitle(movieName: String) = launch {
        if (movieName.isNotBlank()) {
            var moviesResponse = themoviedbRepository.getSearchedMovies(movieName)

            addCategory(moviesResponse)
            addDirector(moviesResponse)

            movies.postValue(moviesResponse)
        }
    }

    fun handleSearchByPerson(personName: String) = launch {
        if (personName.isNotBlank()) {
            var persons = themoviedbRepository.getSeachedPersons(personName)
            var person = persons.find { it.known_for_department == "Directing" }
            if (person != null) {
                var credits = themoviedbRepository.getPersonMovies(person.id)
                var moviesData: ArrayList<Movie> = ArrayList()
                credits.forEach { if (it.job == "Director") moviesData.add(mapToMovie(it)) }

                addCategory(moviesData)
                addDirector(moviesData)

                movies.postValue(moviesData)
            }
        }
    }

    private fun addCategory(movies: List<Movie>) {
        if (movies.isNotEmpty()) {
            for (index in movies.indices) {
                if (!movies[index].genre_ids.isNullOrEmpty()) {
                    movies[index].genre = ""
                    for (indexGenreIds in movies[index].genre_ids?.indices!!) {
                        for (indexGenreList in genresList.indices) {
                            if (movies[index].genre_ids?.get(indexGenreIds) == genresList[indexGenreList].id) {
                                movies[index].genre += genresList[indexGenreList].name + " "
                            }
                        }
                    }
                } else {
                    movies[index].genre = "No category"
                }
            }
        }
    }

    private suspend fun addDirector(movies: List<Movie>) {
        for (index in movies.indices) {
            movies[index].director = ""
            themoviedbRepository.getCrew(movies[index].id.toString())
                .filter { it.job == "Director" }
                .forEach { movies[index].director += it.name + " " }

            if (movies[index].director.isEmpty()) {
                movies[index].director = "No director"
            }
        }
    }


    private fun mapToMovie(personCrew: PersonCrew): Movie {
        return Movie(
            personCrew.popularity,
            personCrew.id,
            personCrew.vote_count,
            personCrew.vote_average,
            personCrew.title,
            personCrew.release_date,
            personCrew.original_language,
            personCrew.original_title,
            personCrew.genre_ids,
            personCrew.backdrop_path,
            personCrew.adult,
            personCrew.overview,
            personCrew.poster_path,
            "",
            ""
        )
    }
}

