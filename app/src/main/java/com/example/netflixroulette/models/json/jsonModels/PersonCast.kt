package com.example.netflixroulette.models.json.jsonModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonCast(
    val character: String,
    val credit_id: String,
    val release_date: String,
    val vote_count: Int,
    val video: Boolean,
    val adult: Boolean,
    val vote_average: Double,
    val title: String,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val popularity: Double,
    val id: Int,
    val backdrop_path: String,
    val overview: String,
    val poster_path: String
) : Parcelable