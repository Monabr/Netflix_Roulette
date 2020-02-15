package com.example.netflixroulette.network

import javax.inject.Inject

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