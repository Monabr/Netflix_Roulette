package com.example.netflixroulette.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


interface AssistedSavedStateViewModelFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}
