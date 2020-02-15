package com.example.netflixroulette.dagger

import com.example.netflixroulette.dagger.modules.*
import com.example.netflixroulette.views.DetailsFragment
import com.example.netflixroulette.views.SavedMoviesFragment
import com.example.netflixroulette.views.SearchWithFragment
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
    fun inject(detailsFragment: DetailsFragment)


    @Component.Builder
    interface Builder {
        fun initDBModule(dbModule: DBModule): Builder

        fun build(): AppComponent
    }
}