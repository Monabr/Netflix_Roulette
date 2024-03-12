package com.example.netflixroulette.models.json.jsonResponse

import com.example.netflixroulette.models.json.jsonModels.Person
import kotlinx.serialization.Serializable

@Serializable
class ResponsePerson (
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : List<Person>
)