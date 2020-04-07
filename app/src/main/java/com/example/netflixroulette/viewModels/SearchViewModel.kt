package com.example.netflixroulette.viewModels

import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixroulette.models.json.jsonModels.Genre
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.models.json.jsonModels.PersonCrew
import com.example.netflixroulette.repository.network.ThemoviedbRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
     * @param context for [Toast.makeText]
     * @param movieName just movie name
     */
    fun handleSearchByTitle(context: Context, movieName: String) = launch {
        if (movieName.isNotBlank()) {
            try {
                if (genresList.isEmpty()) {
                    genresList = themoviedbRepository.getGenres()
                }
                var moviesResponse = themoviedbRepository.getSearchedMovies(movieName)

                addCategory(moviesResponse)
                addDirector(moviesResponse)

                movies.postValue(moviesResponse)
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    handleException(context, ex)
                }
            }
        }
    }

    /**
     * Handle search request to search using name of movie director and post value to [movies]
     *
     * @param context for [Toast.makeText]
     * @param personName just name of movie director
     */
    fun handleSearchByPerson(context: Context, personName: String) = launch {
        if (personName.isNotBlank()) {
            try {
                if (genresList.isEmpty()) {
                    genresList = themoviedbRepository.getGenres()
                }
                var persons = themoviedbRepository.getSearchedPersons(personName)
                var person = persons.find { it.known_for_department == "Directing" }
                if (person != null) {
                    var credits = themoviedbRepository.getPersonMovies(person.id)
                    var moviesData: ArrayList<Movie> = ArrayList()
                    credits.forEach { if (it.job == "Director") moviesData.add(mapToMovie(it)) }

                    addCategory(moviesData)
                    addDirector(moviesData)

                    movies.postValue(moviesData)
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    handleException(context, ex)
                }
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
            themoviedbRepository.getCrew(movies[index].id.toString())
                .filter { it.job == "Director" }
                .forEach { movies[index].director += it.name + " " }

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

    /**
     * Handle exception and make message after it
     *
     * @param context for [Toast.makeText]
     * @param ex actual exception to handle
     */
    private fun handleException(context: Context, ex: Exception) {
        when (ex) {
            is UnknownHostException -> Toast.makeText(context, "No internet", Toast.LENGTH_LONG)
                .show()
            is HttpException -> {
                when (ex.code()) {
                    400 -> Toast.makeText(
                        context,
                        "The server did not understand the request.",
                        Toast.LENGTH_LONG
                    ).show()
                    401 -> Toast.makeText(
                        context,
                        "The requested result needs a username and a password.",
                        Toast.LENGTH_LONG
                    ).show()
                    402 -> Toast.makeText(context, "Payment Required", Toast.LENGTH_LONG).show()
                    403 -> Toast.makeText(
                        context,
                        "Access is forbidden to the requested result.",
                        Toast.LENGTH_LONG
                    ).show()
                    404 -> Toast.makeText(
                        context,
                        "The server can not find the requested result.",
                        Toast.LENGTH_LONG
                    ).show()
                    405 -> Toast.makeText(
                        context,
                        "The method specified in the request is not allowed.",
                        Toast.LENGTH_LONG
                    ).show()
                    406 -> Toast.makeText(
                        context,
                        "The server can only generate a response that is not accepted by the client.",
                        Toast.LENGTH_LONG
                    ).show()
                    407 -> Toast.makeText(
                        context,
                        "You must authenticate with a proxy server before this request can be served.",
                        Toast.LENGTH_LONG
                    ).show()
                    408 -> Toast.makeText(
                        context,
                        "The request took longer than the server was prepared to wait.",
                        Toast.LENGTH_LONG
                    ).show()
                    409 -> Toast.makeText(
                        context,
                        "The request could not be completed because of a conflict.",
                        Toast.LENGTH_LONG
                    ).show()
                    410 -> Toast.makeText(
                        context,
                        "The requested result is no longer available.",
                        Toast.LENGTH_LONG
                    ).show()
                    411 -> Toast.makeText(
                        context,
                        "The \"Content-Length\" is not defined. The server will not accept the request without it.",
                        Toast.LENGTH_LONG
                    ).show()
                    412 -> Toast.makeText(
                        context,
                        "The pre condition given in the request evaluated to false by the server.",
                        Toast.LENGTH_LONG
                    ).show()
                    413 -> Toast.makeText(
                        context,
                        "The server will not accept the request, because the request entity is too large.",
                        Toast.LENGTH_LONG
                    ).show()
                    414 -> Toast.makeText(
                        context,
                        "The server will not accept the request, because the url is too long.",
                        Toast.LENGTH_LONG
                    ).show()
                    415 -> Toast.makeText(
                        context,
                        "The server will not accept the request, because the media type is not supported.",
                        Toast.LENGTH_LONG
                    ).show()
                    416 -> Toast.makeText(
                        context,
                        "The requested byte range is not available and is out of bounds.",
                        Toast.LENGTH_LONG
                    ).show()
                    417 -> Toast.makeText(
                        context,
                        "The expectation given in an Expect request-header field could not be met by this server.",
                        Toast.LENGTH_LONG
                    ).show()
                    500 -> Toast.makeText(
                        context,
                        "The request was not completed. The server met an unexpected condition.",
                        Toast.LENGTH_LONG
                    ).show()
                    else -> Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
                }
            }
            else -> Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }

    }
}

