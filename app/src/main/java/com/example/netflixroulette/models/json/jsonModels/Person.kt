package com.example.netflixroulette.models.json.jsonModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person (
    val popularity : Double,
    val known_for_department : String,
    val name : String,
    val id : Int,
    val profile_path : String,
    val adult : Boolean,
    val gender : Int
) : Parcelable