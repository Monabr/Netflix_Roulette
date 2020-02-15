package com.example.netflixroulette.dagger.modules

import com.example.netflixroulette.network.ThemoviedbRepository
import com.example.netflixroulette.network.ThemoviedbRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ThemoviedbRepositoryImplModule {

    @Binds
    abstract fun bindsThemoviedbRepositoryImpl(placeholderRepositoryImpl: ThemoviedbRepositoryImpl): ThemoviedbRepository
}