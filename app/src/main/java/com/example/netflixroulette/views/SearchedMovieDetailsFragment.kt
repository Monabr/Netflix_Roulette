package com.example.netflixroulette.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.netflixroulette.R
import com.example.netflixroulette.adapters.SearchedMovieDetailsAdapter
import com.example.netflixroulette.dagger.AppComponentProvider
import com.example.netflixroulette.models.json.jsonModels.Movie
import com.example.netflixroulette.viewModels.SearchedMovieDetailsViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_details.*

class SearchedMovieDetailsFragment : BaseFragment(), SearchedMovieDetailsAdapter.CallBackAdapterListener {

    /**
     * ViewModel for handling save action or undo save
     */
    private val viewModelSearchedMovie: SearchedMovieDetailsViewModel by viewModels()

    /**
     * Adapter for viewPager
     */
    private lateinit var searchedMovieDetailsAdapter: SearchedMovieDetailsAdapter

    /**
     * Local link on view, we need it for [Snackbar.make] in [SearchedMovieDetailsViewModel.handleActionSave]
     */
    private lateinit var viewLocal: View

    companion object {
        fun newInstance() = SearchedMovieDetailsFragment()

        const val MOVIES = "movies"
        const val CURRENT_ITEM = "current_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponentProvider.provideAppComponent(requireContext()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLocal = inflater.inflate(R.layout.fragment_details, container, false)
        return viewLocal
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.activity_main_container_toolbar?.visibility = View.GONE
        initFields()
        initViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.activity_main_container_toolbar?.visibility = View.VISIBLE
    }

    override fun onAdapterItemBackArrowPressed() {
        activity?.onBackPressed()
    }

    override fun onAdapterItemSavePressed(movie: Movie) {
        viewModelSearchedMovie.handleActionSave(
            viewLocal,
            movie
        )
    }

    private fun initFields() {
        searchedMovieDetailsAdapter = SearchedMovieDetailsAdapter(
            arguments?.getParcelableArrayList<Movie>(MOVIES)?.toList() ?: ArrayList(),
            this)
    }

    private fun initViewPager() {
        fragment_details_vp_main.adapter = searchedMovieDetailsAdapter
        fragment_details_vp_main.setCurrentItem(arguments?.getInt(CURRENT_ITEM, 0) ?: 0, false)
    }
}