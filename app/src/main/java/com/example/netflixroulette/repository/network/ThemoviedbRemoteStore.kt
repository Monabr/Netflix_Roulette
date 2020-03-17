package com.example.netflixroulette.repository.network

import javax.inject.Inject

/**
 * Layer for get exactly data from request result that we need and also make our requests looks nice
 *
 */
class ThemoviedbRemoteStore @Inject constructor(
    private val themoviedbApi: ThemoviedbApi
) {

    suspend fun getSearchedMovies(movieName: String) = themoviedbApi.getSearchedMovieAsync(movieName).await().results

    suspend fun getGenres() = themoviedbApi.getGenresAsync().await().genres

    suspend fun getCrew(movieId: String) = themoviedbApi.getCrewAsync(movieId).await().crew

    suspend fun getSearchedPersons(personName: String) =
        themoviedbApi.getSearchedPersonsAsync(personName).await().results

    suspend fun getPersonMovies(personId: Int) = themoviedbApi.getPersonMoviesAsync(personId).await().crew
}