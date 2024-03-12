package com.example.netflixroulette.models.json.jsonModels

import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val popularity : Double,
    val known_for_department : String,
    val name : String,
    val id : Int,
    val profile_path : String,
    val adult : Boolean,
    val gender : Int
)