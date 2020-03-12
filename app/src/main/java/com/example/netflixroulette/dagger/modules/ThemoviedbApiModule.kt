package com.example.netflixroulette.dagger.modules

import androidx.annotation.NonNull
import com.example.netflixroulette.repository.network.ThemoviedbApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ThemoviedbApiModule {
    companion object {
        private const val BASE_SERVER = "https://api.themoviedb.org/3/"
    }

    @Provides
    @NonNull
    fun provideThemoviedbApi(): ThemoviedbApi = Retrofit.Builder()
        .baseUrl(BASE_SERVER)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ThemoviedbApi::class.java)
}
