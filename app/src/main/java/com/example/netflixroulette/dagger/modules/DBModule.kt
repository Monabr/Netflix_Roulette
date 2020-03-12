package com.example.netflixroulette.dagger.modules

import android.content.Context
import com.example.netflixroulette.repository.database.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class DBModule(val context: Context) {

    @Provides
    fun provideMovieDB() = MovieDatabase.getDatabase(context)
}
