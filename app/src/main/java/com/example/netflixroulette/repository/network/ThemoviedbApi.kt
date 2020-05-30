package com.example.netflixroulette.repository.network

import com.example.netflixroulette.models.json.jsonResponse.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "9d4cbef8fef8814809eead3ab4dcdf92"

/**
 * Retrofit mechanism for using API by making requests
 *
 */
interface ThemoviedbApi {

    @GET("search/movie?api_key=$API_KEY")
    suspend fun getSearchedMovieAsync(@Query("query") movieName: String): Response<ResponseMovie>

    @GET("genre/movie/list?api_key=$API_KEY")
    suspend fun getGenresAsync(): Response<ResponseGenre>

    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    suspend fun getCrewAsync(@Path("movie_id") movieId: String): Response<ResponseCredit>

    @GET("search/person?api_key=$API_KEY")
    suspend fun getSearchedPersonsAsync(@Query("query") personName: String): Response<ResponsePerson>

    @GET("person/{person_id}/movie_credits?api_key=$API_KEY")
    suspend fun getPersonMoviesAsync(@Path("person_id") person_id: Int): Response<ResponsePersonMovieCredis>
}

