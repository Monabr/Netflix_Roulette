package com.example.netflixroulette.ui.saved

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixroulette.R
import com.example.netflixroulette.databinding.FragmentSavedMoviesBinding
import com.example.netflixroulette.ui.MainContainerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedMoviesFragment : Fragment() {
    private var _binding: FragmentSavedMoviesBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel for get saved movies from database
     */
    private val viewModel: SavedMoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSavedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.onActivityCreated()
        checkMenu()
        initLayoutManager()
        initObserver()
    }

    /**
     * This event is using for save position of items list when we left fragment by clicking on item
     *
     */
    override fun onStop() {
        viewModel.scrollPosition = binding.fragmentSavedMoviesRvMoviesList.layoutManager?.onSaveInstanceState()
        super.onStop()
    }

    /**
     * Simply checking menu item because we enter to this fragment
     *
     */
    private fun checkMenu() {
        (activity as MainContainerActivity).setNavItemChecked(0)
    }

    /**
     * Layout manager initialization depends of devise orientation and also we restoring position of items list
     *
     */
    private fun initLayoutManager() {
        binding.fragmentSavedMoviesRvMoviesList.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }

        binding.fragmentSavedMoviesRvMoviesList.layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)
    }

    /**
     * Observer initialization for getting data and using mechanism of saving and restore position of items list to prevent flickering
     *
     */
    private fun initObserver() {
        viewModel.movies.removeObservers(this)
        viewModel.movies.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.fragmentSavedMoviesRvMoviesList.run {
                    viewModel.scrollPosition = layoutManager?.onSaveInstanceState()
                    adapter = SavedMovieAdapter(it).apply {
                        layoutAnimation = AnimationUtils.loadLayoutAnimation(
                            context,
                            R.anim.layout_animation_from_right
                        )
                        scheduleLayoutAnimation()
                    }
                    layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)
                    binding.fragmentSavedMoviesTvLabelForMovie.visibility = View.GONE
                }
            }
            binding.fragmentSavedMoviesPbLoad.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = SavedMoviesFragment()
    }

}
