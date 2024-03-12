package com.example.netflixroulette.models.json.jsonResponse

import com.example.netflixroulette.models.json.jsonModels.Cast
import com.example.netflixroulette.models.json.jsonModels.Crew
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCredit(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)