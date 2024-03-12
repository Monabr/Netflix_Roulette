package com.example.netflixroulette.models.json.jsonModels

import kotlinx.serialization.Serializable

@Serializable
data class Genre (
    val id : String,
    val name : String
)