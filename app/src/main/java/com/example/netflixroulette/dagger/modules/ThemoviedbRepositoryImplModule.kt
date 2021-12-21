package com.example.netflixroulette.dagger.modules

import com.example.netflixroulette.repository.ThemoviedbRepository
import com.example.netflixroulette.repository.ThemoviedbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemoviedbRepositoryImplModule {

    @Binds
    abstract fun bindsThemoviedbRepositoryImpl(placeholderRepositoryImpl: ThemoviedbRepositoryImpl): ThemoviedbRepository
}