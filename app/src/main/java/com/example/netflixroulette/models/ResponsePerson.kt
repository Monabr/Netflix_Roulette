package com.example.netflixroulette.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponsePerson (
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : List<Person>
) : Parcelable