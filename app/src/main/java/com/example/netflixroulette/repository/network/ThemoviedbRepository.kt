package com.example.netflixroulette.repository.network

import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.models.json.jsonResponse.*
import retrofit2.Response

/**
 * An abstraction according to Repository pattern
 *
 */
interface ThemoviedbRepository {
    suspend fun getSearchedMovies(movieName: String): Response<ResponseMovie>
    suspend fun getGenres(): Response<ResponseGenre>
    suspend fun getCrew(movieId: String): Response<ResponseCredit>
    suspend fun getAllMovies():List<MovieDB>
    suspend fun insertMovie(movie: MovieDB)
    suspend fun deleteMovie(movie: MovieDB)
    suspend fun getMovieById(id: Int): MovieDB?
    suspend fun getSearchedPersons(personName: String): Response<ResponsePerson>
    suspend fun getPersonMovies(personId: Int): Response<ResponsePersonMovieCredis>
}