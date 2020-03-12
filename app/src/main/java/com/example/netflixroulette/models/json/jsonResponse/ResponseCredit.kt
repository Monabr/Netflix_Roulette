package com.example.netflixroulette.models.json.jsonResponse

import android.os.Parcelable
import com.example.netflixroulette.models.json.jsonModels.Cast
import com.example.netflixroulette.models.json.jsonModels.Crew
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCredit(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
) : Parcelable