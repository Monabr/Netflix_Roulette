package com.example.netflixroulette.dagger.modules

import android.content.Context
import com.example.netflixroulette.repository.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    fun provideMovieDB(@ApplicationContext context: Context) = MovieDatabase.getDatabase(context)
}
