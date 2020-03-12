package com.example.netflixroulette.models.json.jsonResponse

import android.os.Parcelable
import com.example.netflixroulette.models.json.jsonModels.PersonCast
import com.example.netflixroulette.models.json.jsonModels.PersonCrew
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponsePersonMovieCredis (
    val cast : List<PersonCast>,
    val crew : List<PersonCrew>,
    val id : Int
) : Parcelable