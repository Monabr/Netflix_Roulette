package com.example.netflixroulette.repository.database

import androidx.room.*
import com.example.netflixroulette.models.db.MovieDB

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: MovieDB)

    @Update
    suspend fun update(movie: MovieDB)

    @Delete
    suspend fun delete(movie: MovieDB)

    @Query("DELETE FROM movieDB")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movieDB")
    suspend fun getAllMovies(): List<MovieDB>

    @Query("SELECT * FROM movieDB WHERE id LIKE :id")
    suspend fun getMovieById(id: Int): MovieDB?
}