package com.example.netflixroulette.repository.network

import javax.inject.Inject

/**
 * Layer for get exactly data from request result that we need and also make our requests looks nice
 *
 */
class ThemoviedbRemoteStore @Inject constructor(
    private val themoviedbApi: ThemoviedbApi
) {

    suspend fun getSearchedMovies(movieName: String) = themoviedbApi.getSearchedMovieAsync(movieName)

    suspend fun getGenres() = themoviedbApi.getGenresAsync()

    suspend fun getCrew(movieId: String) = themoviedbApi.getCrewAsync(movieId)

    suspend fun getSearchedPersons(personName: String) = themoviedbApi.getSearchedPersonsAsync(personName)

    suspend fun getPersonMovies(personId: Int) = themoviedbApi.getPersonMoviesAsync(personId)
}