package com.example.netflixroulette.dagger.modules

import androidx.lifecycle.ViewModel
import com.example.netflixroulette.dagger.ViewModelKey
import com.example.netflixroulette.viewModels.SavedMovieDetailsViewModel
import com.example.netflixroulette.viewModels.SavedMoviesViewModel
import com.example.netflixroulette.viewModels.SearchViewModel
import com.example.netflixroulette.viewModels.SearchedMovieDetailsViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_ViewModelsModule::class])
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SavedMoviesViewModel::class)
    abstract fun bindsMainViewModel(vm: SavedMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindsSearchViewModel(vm: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchedMovieDetailsViewModel::class)
    abstract fun bindsSearchedMovieDetailsViewModel(vm: SearchedMovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedMovieDetailsViewModel::class)
    abstract fun bindsSavedMovieDetailsViewModel(vm: SavedMovieDetailsViewModel): ViewModel
}