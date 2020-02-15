package com.example.netflixroulette.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonCrew (
    val id: Int,
    val department: String,
    val original_language: String,
    val original_title: String,
    val job: String,
    val overview: String,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    val backdrop_path: String,
    val title: String,
    val popularity: Double,
    val genre_ids: ArrayList<String>,
    val vote_average: Double,
    val adult: Boolean,
    val release_date: String,
    val credit_id: String
) : Parcelable