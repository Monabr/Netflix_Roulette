package com.example.netflixroulette.network

import com.example.netflixroulette.models.*

interface ThemoviedbRepository {
    suspend fun getSearchedMovies(movieName: String): List<Movie>
    suspend fun getGenres():List<Genre>
    suspend fun getCrew(movieId: String):List<Crew>
    suspend fun getAllMovies():List<Movie>
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
    suspend fun getMovieById(id: Int): Movie?
    suspend fun getSeachedPersons(personName: String): List<Person>
    suspend fun getPersonMovies(personId: Int): List<PersonCrew>
}