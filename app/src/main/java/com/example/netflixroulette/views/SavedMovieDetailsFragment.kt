package com.example.netflixroulette.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.netflixroulette.R
import com.example.netflixroulette.adapters.SavedMovieDetailsAdapter
import com.example.netflixroulette.dagger.AppComponentProvider
import com.example.netflixroulette.models.db.MovieDB
import com.example.netflixroulette.viewModels.SavedMovieDetailsViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_details.*

class SavedMovieDetailsFragment : BaseFragment(), SavedMovieDetailsAdapter.CallBackAdapterListener {

    private val viewModel: SavedMovieDetailsViewModel by viewModels()

    private lateinit var savedMovieDetailsAdapter: SavedMovieDetailsAdapter
    private lateinit var viewLocal: View
    private lateinit var movies: List<MovieDB>

    companion object {
        fun newInstance() = SavedMovieDetailsFragment()

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

    override fun onStop() {
        activity?.activity_main_container_toolbar?.visibility = View.VISIBLE
        super.onStop()
    }

    override fun onAdapterItemBackPressed() {
        activity?.onBackPressed()
    }

    override fun onAdapterItemSavePressed(movie: MovieDB) {
        viewModel.handleActionSave(
            viewLocal,
            movie
        )
    }

    private fun initViewPager() {
        fragment_details_vp_main.adapter = savedMovieDetailsAdapter
        fragment_details_vp_main.setCurrentItem(arguments?.getInt(CURRENT_ITEM, 0) ?: 0, false)
    }

    private fun initFields() {
        movies = arguments?.getParcelableArrayList<MovieDB>(MOVIES)?.toList() ?: ArrayList()
        savedMovieDetailsAdapter = SavedMovieDetailsAdapter(movies, this)
    }
}