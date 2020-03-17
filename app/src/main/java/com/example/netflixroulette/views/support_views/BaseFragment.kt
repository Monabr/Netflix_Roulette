package com.example.netflixroulette.views.support_views

import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.example.netflixroulette.factory.InjectingSavedStateViewModelFactory
import javax.inject.Inject

/**
 * Use this fragment as parent get your viewModel injected using [viewModels()][androidx.fragment.app.viewModels]
 *
 * ```
 * class MyFragment : Fragment() {
 *     val viewModel: MYViewModel by viewModels()
 * }
 * ```
 *
 * Also you should define your viewModel in [ViewModelsModule.kt][com.example.netflixroulette.dagger.modules.ViewModelsModule]
 */
abstract class BaseFragment : Fragment(), HasDefaultViewModelProviderFactory {

    @Inject
    lateinit var defaultViewModelFactory: InjectingSavedStateViewModelFactory

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.create(this, arguments)
}
