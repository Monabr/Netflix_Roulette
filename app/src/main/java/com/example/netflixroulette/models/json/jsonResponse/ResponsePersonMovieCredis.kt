package com.example.netflixroulette.models.json.jsonResponse

import com.example.netflixroulette.models.json.jsonModels.PersonCast
import com.example.netflixroulette.models.json.jsonModels.PersonCrew
import kotlinx.serialization.Serializable

@Serializable
class ResponsePersonMovieCredis (
    val cast : List<PersonCast>,
    val crew : List<PersonCrew>,
    val id : Int
)