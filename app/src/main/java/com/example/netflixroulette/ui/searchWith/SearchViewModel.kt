package com.example.netflixroulette.ui.searchWith

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.helpers.ErrorHandler
import com.example.netflixroulette.models.json.jsonModels.Genre
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.models.json.jsonModels.PersonCrew
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SearchViewModel @Inject constructor(
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
     * Local list of movies genres. Using to name genres of movie depends of genre id from [Movie.genre_ids]
     */
    private var genresList: List<Genre> = emptyList()

    /**
     * List of movies that will be observed by fragment
     */
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    /**
     * Error code for observing in fragment
     */
    var error: MutableLiveData<Int> = MutableLiveData()

    /**
     * Saved position of items list
     */
    var scrollPosition: Parcelable? = null

    /**
     * Using to cancel all coroutines when we no need it by [cancelChildren]
     *
     */
    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    /**
     * Handle search request to search using title and post value to [movies]
     *
     * @param movieName just movie name
     */
    fun handleSearchByTitle(movieName: String) = launch {
        if (movieName.isNotBlank()) {
            try {
                if (genresList.isEmpty()) {
                    val genresListResponse = themoviedbRepository.getGenres()
                    val genresListResponseBody = genresListResponse.body()
                    if (genresListResponse.isSuccessful && genresListResponseBody != null) {
                        genresList = genresListResponseBody.genres
                    } else {
                        error.postValue(genresListResponse.code())
                        return@launch
                    }
                }

                val moviesResponse = themoviedbRepository.getSearchedMovies(movieName)

                if (moviesResponse.isSuccessful) {
                    val movieBody = moviesResponse.body()
                    if (movieBody != null && !movieBody.results.isNullOrEmpty()) {
                        var movieList = movieBody.results

                        addCategory(movieList)
                        addDirector(movieList)

                        movies.postValue(movieList)
                    } else {
                        movies.postValue(emptyList())
                    }
                } else {
                    error.postValue(moviesResponse.code())
                }
            } catch (ex: Exception) {
                error.postValue(ErrorHandler.SERVICE_UNAVAILABLE)
            }
        }
    }

    /**
     * Handle search request to search using name of movie director and post value to [movies]
     *
     * @param personName just name of movie director
     */
    fun handleSearchByPerson(personName: String) = launch {
        if (personName.isNotBlank()) {
            try {
                val genresListResponse = themoviedbRepository.getGenres()
                val genresListResponseBody = genresListResponse.body()
                if (genresListResponse.isSuccessful && genresListResponseBody != null) {
                    genresList = genresListResponseBody.genres
                } else {
                    error.postValue(genresListResponse.code())
                    return@launch
                }

                val personsResponse = themoviedbRepository.getSearchedPersons(personName)

                if (personsResponse.isSuccessful) {
                    val personBody = personsResponse.body()
                    if (personBody != null && !personBody.results.isNullOrEmpty()) {

                        val persons = personBody.results
                        val person = persons.find { it.known_for_department == "Directing" }
                        if (person != null) {
                            val creditsResponse = themoviedbRepository.getPersonMovies(person.id)

                            if (creditsResponse.isSuccessful) {
                                val credits = creditsResponse.body()?.crew
                                val moviesData = mutableListOf<Movie>()
                                credits?.forEach { if (it.job == "Director") moviesData.add(mapToMovie(it)) }

                                addCategory(moviesData)
                                addDirector(moviesData)

                                movies.postValue(moviesData)
                            } else {
                                error.postValue(genresListResponse.code())
                                return@launch
                            }
                        } else {
                            movies.postValue(emptyList())
                        }
                    } else {
                        movies.postValue(emptyList())
                    }
                } else {
                    error.postValue(personsResponse.code())
                }
            } catch (ex: Exception) {
                error.postValue(ErrorHandler.SERVICE_UNAVAILABLE)
            }
        }
    }

    /**
     * Add name of category for every movie in the list using [genresList]
     *
     * @param movies just list of movie to edit
     */
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

    /**
     * Search for director for every movie in the list
     *
     * @param movies just list of movie to edit
     */
    private suspend fun addDirector(movies: List<Movie>) {
        for (index in movies.indices) {
            movies[index].director = ""
            val crewResponse = themoviedbRepository.getCrew(movies[index].id.toString())
            if (crewResponse.isSuccessful) {
                crewResponse.body()?.crew?.filter { it.job == "Director" }
                    ?.forEach { movies[index].director += it.name + " " }
            } else {
                throw HttpException(crewResponse)
            }
            if (movies[index].director.isEmpty()) {
                movies[index].director = "No director"
            }
        }
    }

    /**
     * Convert results for [Movie] object to use it in application
     *
     * @param personCrew object to convert
     * @return usual [Movie] object
     */
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

