package com.example.netflixroulette.dagger.modules

import androidx.lifecycle.ViewModel
import com.example.netflixroulette.dagger.ViewModelKey
import com.example.netflixroulette.viewModels.DetailsViewModel
import com.example.netflixroulette.viewModels.SavedMoviesViewModel
import com.example.netflixroulette.viewModels.SearchViewModel
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
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindsDetailsViewModel(vm: DetailsViewModel): ViewModel

}