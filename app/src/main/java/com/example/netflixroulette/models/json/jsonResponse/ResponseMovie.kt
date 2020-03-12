package com.example.netflixroulette.models.json.jsonResponse

import android.os.Parcelable
import com.example.netflixroulette.models.json.jsonModels.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseMovie (
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : List<Movie>
) : Parcelable