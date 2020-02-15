package com.example.netflixroulette.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCredit(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
) : Parcelable