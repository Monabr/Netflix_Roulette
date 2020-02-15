package com.example.netflixroulette.dagger.modules

import android.app.Application
import android.content.Context
import com.example.netflixroulette.database.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class DBModule(val context: Context) {

    @Provides
    fun provideMovieDB() = MovieDatabase.getDatabase(context)
}
