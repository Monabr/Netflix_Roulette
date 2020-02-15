package com.example.netflixroulette.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.netflixroulette.R
import com.example.netflixroulette.adapters.MovieDetailsAdapter
import com.example.netflixroulette.dagger.AppComponentProvider
import com.example.netflixroulette.models.Movie
import com.example.netflixroulette.viewModels.DetailsViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import kotlinx.android.synthetic.main.activity_main_container.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment(), MovieDetailsAdapter.CallBackAdapterListener {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var viewLocal: View
    private lateinit var movies: List<Movie>

    companion object {
        fun newInstance() = DetailsFragment()

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

    override fun onAdapterItemSavePressed(movie: Movie) {
        viewModel.handleActionSave(
            viewLocal,
            movie
        )
    }

    private fun initViewPager() {
        fragment_details_vp_main.adapter = movieDetailsAdapter
        fragment_details_vp_main.setCurrentItem(arguments?.getInt(CURRENT_ITEM, 0) ?: 0, false)
    }

    private fun initFields() {
        movies = arguments?.getParcelableArrayList<Movie>(MOVIES)?.toList() ?: ArrayList()
        movieDetailsAdapter = MovieDetailsAdapter(movies, this)
    }
}