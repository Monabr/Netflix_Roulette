package com.example.netflixroulette.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseGenre (
    val genres : List<Genre>
) : Parcelable