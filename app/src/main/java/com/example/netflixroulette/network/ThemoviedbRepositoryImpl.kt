package com.example.netflixroulette.network

import com.example.netflixroulette.database.MovieDatabase
import com.example.netflixroulette.models.Movie
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ThemoviedbRepositoryImpl @Inject constructor(
    private val themoviedbRemoteStore: ThemoviedbRemoteStore,
    private val movieDatabase: MovieDatabase
) : ThemoviedbRepository {


    override suspend fun getSearchedMovies(movieName: String) = themoviedbRemoteStore.getSearchedMovies(movieName)

    override suspend fun getGenres() = themoviedbRemoteStore.getGenres()

    override suspend fun getCrew(movieId: String) = themoviedbRemoteStore.getCrew(movieId)

    override suspend fun getAllMovies() = movieDatabase.movieDao().getAllMovies()

    override suspend fun insertMovie(movie: Movie) = movieDatabase.movieDao().insert(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDatabase.movieDao().delete(movie)

    override suspend fun getMovieById(id: Int) = movieDatabase.movieDao().getMovieById(id)

    override suspend fun getSeachedPersons(personName: String) =
        themoviedbRemoteStore.getSearchedPersons(personName)

    override suspend fun getPersonMovies(personId: Int) = themoviedbRemoteStore.getPersonMovies(personId)


}