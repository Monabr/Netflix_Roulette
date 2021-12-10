package com.example.netflixroulette.models.json.jsonModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val popularity: Double,
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
) : Parcelable
