package com.example.netflixroulette.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponsePersonMovieCredis (
    val cast : List<PersonCast>,
    val crew : List<PersonCrew>,
    val id : Int
) : Parcelable