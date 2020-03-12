package com.example.netflixroulette.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixroulette.R
import com.example.netflixroulette.adapters.SavedMovieAdapter
import com.example.netflixroulette.dagger.AppComponentProvider
import com.example.netflixroulette.viewModels.SavedMoviesViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import com.example.netflixroulette.views.support_views.MainContainerActivity
import kotlinx.android.synthetic.main.fragment_saved_movies.*


class SavedMoviesFragment : BaseFragment() {

    private val viewModel: SavedMoviesViewModel by viewModels()

    companion object {
        fun newInstance() = SavedMoviesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponentProvider.provideAppComponent(requireContext()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_saved_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.onActivityCreated()
        checkMenu()
        initAdapter()
        initObserver()
    }

    override fun onStop() {
        viewModel.scrollPosition =
            fragment_start_rv_movies_list.layoutManager?.onSaveInstanceState()
        super.onStop()
    }

    private fun initObserver() {
        viewModel.movies.removeObservers(this)
        viewModel.movies.observe(this) {
            if (!it.isNullOrEmpty()) {
                fragment_start_rv_movies_list.run {
                    viewModel.scrollPosition = layoutManager?.onSaveInstanceState()
                    adapter = SavedMovieAdapter(it).apply {
                        layoutAnimation = AnimationUtils.loadLayoutAnimation(
                            context,
                            R.anim.layout_animation_from_right
                        )
                        scheduleLayoutAnimation()
                    }

                    layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)
                    fragment_start_tv_label_for_movie.visibility = View.GONE
                }
            }
            fragment_start_pb_load.visibility = View.GONE

        }
    }

    private fun initAdapter() {
        fragment_start_rv_movies_list.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }

        fragment_start_rv_movies_list.layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)
    }

    private fun checkMenu() {
        (activity as MainContainerActivity).setNavItemChecked(0)
    }
}
