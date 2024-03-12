package com.example.netflixroulette.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Movie model exactly for database because of Clean Architecture
 *
 */
@Serializable
@Entity
data class MovieDB(
    val popularity: Double,
    @PrimaryKey
    val id: Int,
    val vote_count: Int,
    val vote_average: Double,
    val title: String?,
    val release_date: String?,
    val original_language: String?,
    val original_title: String?,
    val genre_ids: List<String>?,
    val backdrop_path: String?,
    val adult: Boolean?,
    val overview: String?,
    val poster_path: String?,
    var genre: String,
    var director: String
)