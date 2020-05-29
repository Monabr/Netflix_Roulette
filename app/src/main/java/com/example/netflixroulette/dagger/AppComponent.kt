package com.example.netflixroulette.dagger

import android.content.Context
import com.example.netflixroulette.dagger.modules.*
import com.example.netflixroulette.views.SavedMovieDetailsFragment
import com.example.netflixroulette.views.SavedMoviesFragment
import com.example.netflixroulette.views.SearchWithFragment
import com.example.netflixroulette.views.SearchedMovieDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelsModule::class,
    MapViewModelsModule::class,
    ThemoviedbApiModule::class,
    ThemoviedbRepositoryImplModule::class,
    DBModule::class])
interface AppComponent {

    fun inject(savedMoviesFragment: SavedMoviesFragment)
    fun inject(searchWithFragment: SearchWithFragment)
    fun inject(searchedMovieDetailsFragment: SearchedMovieDetailsFragment)
    fun inject(savedMovieDetailsFragment: SavedMovieDetailsFragment)


    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
}