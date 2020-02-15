package com.example.netflixroulette.network

import com.example.netflixroulette.models.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "9d4cbef8fef8814809eead3ab4dcdf92"

interface ThemoviedbApi {

    @GET("search/movie?api_key=$API_KEY")
    fun getSearchedMovieAsync(@Query("query") movieName: String): Deferred<ResponseMovie>

    @GET("genre/movie/list?api_key=$API_KEY")
    fun getGenresAsync(): Deferred<ResponseGenre>

    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    fun getCrewAsync(@Path("movie_id") movieId: String): Deferred<ResponseCredit>

    @GET("search/person?api_key=$API_KEY")
    fun getSearchedPersonsAsync(@Query("query") personName: String): Deferred<ResponsePerson>

    @GET("person/{person_id}/movie_credits?api_key=$API_KEY")
    fun getPersonMoviesAsync(@Path("person_id") person_id: Int): Deferred<ResponsePersonMovieCredis>
}

