package com.example.netflixroulette.models.json.jsonResponse

import android.os.Parcelable
import com.example.netflixroulette.models.json.jsonModels.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGenre (
    val genres : List<Genre>
) : Parcelable