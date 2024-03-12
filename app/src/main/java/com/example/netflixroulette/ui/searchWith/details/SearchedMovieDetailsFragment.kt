package com.example.netflixroulette.ui.searchWith.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.netflixroulette.databinding.FragmentDetailsBinding
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.ui.MainContainerActivity
import com.example.netflixroulette.ui.saved.details.SavedMovieDetailsFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class SearchedMovieDetailsFragment : Fragment(), SearchedMovieDetailsAdapter.CallBackAdapterListener {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel for handling save action or undo save
     */
    private val viewModelSearchedMovie: SearchedMovieDetailsViewModel by viewModels()

    /**
     * Adapter for viewPager
     */
    private lateinit var searchedMovieDetailsAdapter: SearchedMovieDetailsAdapter

    companion object {
        fun newInstance() = SearchedMovieDetailsFragment()

        const val MOVIES = "movies"
        const val CURRENT_ITEM = "current_item"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainContainerActivity).binding.activityMainContainerToolbar.visibility = View.GONE
        initFields()
        initViewPager()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainContainerActivity).binding.activityMainContainerToolbar.visibility = View.VISIBLE
    }

    override fun onAdapterItemBackArrowPressed() {
        activity?.onBackPressed()
    }

    override fun onAdapterItemSavePressed(movie: Movie) {
        viewModelSearchedMovie.handleActionSave(movie)
    }

    private fun initFields() {
        searchedMovieDetailsAdapter = SearchedMovieDetailsAdapter(
            arguments?.getString(SavedMovieDetailsFragment.MOVIES)?.let { Json.decodeFromString<List<Movie>>(it) } ?: ArrayList(),
            this
        )
    }

    private fun initViewPager() {
        binding.fragmentDetailsVpMain.adapter = searchedMovieDetailsAdapter
        binding.fragmentDetailsVpMain.setCurrentItem(arguments?.getInt(CURRENT_ITEM, 0) ?: 0, false)
    }

    private fun initObservers() {
        viewModelSearchedMovie.isSaved.observe(this.viewLifecycleOwner) {
            if (it) {
                view?.let { viewNotNull -> Snackbar.make(viewNotNull, "Saved", Snackbar.LENGTH_SHORT).show() }
            } else {
                view?.let { viewNotNull -> Snackbar.make(viewNotNull, "Deleted", Snackbar.LENGTH_SHORT).show() }
            }
        }
    }
}