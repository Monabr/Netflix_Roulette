package com.example.netflixroulette.dagger.modules

import androidx.annotation.NonNull
import com.example.netflixroulette.repository.network.ThemoviedbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ThemoviedbApiModule {
    companion object {
        private const val BASE_SERVER = "https://api.themoviedb.org/3/"
    }

    @Provides
    @NonNull
    fun provideThemoviedbApi(): ThemoviedbApi = Retrofit.Builder()
        .baseUrl(BASE_SERVER)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ThemoviedbApi::class.java)
}
