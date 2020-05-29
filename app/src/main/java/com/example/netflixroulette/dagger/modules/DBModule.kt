package com.example.netflixroulette.dagger.modules

import android.content.Context
import com.example.netflixroulette.repository.database.MovieDatabase
import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    fun provideMovieDB(context: Context) = MovieDatabase.getDatabase(context)
}
