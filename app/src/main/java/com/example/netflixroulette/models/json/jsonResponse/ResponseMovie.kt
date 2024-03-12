package com.example.netflixroulette.models.json.jsonResponse

import com.example.netflixroulette.models.json.jsonModels.Movie
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovie (
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : List<Movie>
)