package com.example.netflixroulette.models.json.jsonModels

import kotlinx.serialization.Serializable

@Serializable
data class Crew (
    val credit_id : String,
    val department : String,
    val gender : Int,
    val id : Int,
    val job : String,
    val name : String,
    val profile_path : String
)