package com.example.netflixroulette.models.json.jsonModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew (
    val credit_id : String,
    val department : String,
    val gender : Int,
    val id : Int,
    val job : String,
    val name : String,
    val profile_path : String
) : Parcelable