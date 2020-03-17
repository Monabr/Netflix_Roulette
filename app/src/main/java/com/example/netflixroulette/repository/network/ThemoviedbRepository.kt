package com.example.netflixroulette.repository.network

import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.models.json.jsonModels.*

/**
 * An abstraction according to Repository pattern
 *
 */
interface ThemoviedbRepository {
    suspend fun getSearchedMovies(movieName: String): List<Movie>
    suspend fun getGenres():List<Genre>
    suspend fun getCrew(movieId: String):List<Crew>
    suspend fun getAllMovies():List<MovieDB>
    suspend fun insertMovie(movie: MovieDB)
    suspend fun deleteMovie(movie: MovieDB)
    suspend fun getMovieById(id: Int): MovieDB?
    suspend fun getSearchedPersons(personName: String): List<Person>
    suspend fun getPersonMovies(personId: Int): List<PersonCrew>
}