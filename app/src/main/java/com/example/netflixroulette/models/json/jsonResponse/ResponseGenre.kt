package com.example.netflixroulette.models.json.jsonResponse

import com.example.netflixroulette.models.json.jsonModels.Genre
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGenre (
    val genres : List<Genre>
)