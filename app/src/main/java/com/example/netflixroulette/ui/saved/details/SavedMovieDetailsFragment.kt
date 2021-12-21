package com.example.netflixroulette.ui.saved.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.netflixroulette.databinding.FragmentDetailsBinding
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.ui.MainContainerActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedMovieDetailsFragment : Fragment(), SavedMovieDetailsAdapter.CallBackAdapterListener {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel for handling delete or save action
     */
    private val viewModel: SavedMovieDetailsViewModel by viewModels()

    /**
     * Adapter for viewPager
     */
    private lateinit var savedMovieDetailsAdapter: SavedMovieDetailsAdapter

    companion object {
        fun newInstance() = SavedMovieDetailsFragment()

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

    override fun onStop() {
        (activity as MainContainerActivity).binding.activityMainContainerToolbar.visibility = View.VISIBLE
        super.onStop()
    }

    override fun onAdapterItemBackArrowPressed() {
        activity?.onBackPressed()
    }

    override fun onAdapterItemSavePressed(movie: MovieDB) {
        viewModel.handleActionSave(movie)
    }

    private fun initFields() {
        savedMovieDetailsAdapter = SavedMovieDetailsAdapter(
            arguments?.getParcelableArrayList<MovieDB>(MOVIES)?.toList() ?: ArrayList(),
            this)
    }

    private fun initViewPager() {
        binding.fragmentDetailsVpMain.adapter = savedMovieDetailsAdapter
        binding.fragmentDetailsVpMain.setCurrentItem(arguments?.getInt(CURRENT_ITEM, 0) ?: 0, false)
    }

    private fun initObservers() {
        viewModel.isSaved.observe(this) {
            if (it) {
                view?.let { viewNotNull -> Snackbar.make(viewNotNull, "Saved", Snackbar.LENGTH_SHORT).show() }
            } else {
                view?.let { viewNotNull -> Snackbar.make(viewNotNull, "Deleted", Snackbar.LENGTH_SHORT).show() }
            }
        }
    }
}