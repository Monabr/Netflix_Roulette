package com.example.netflixroulette.views

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixroulette.R
import com.example.netflixroulette.adapters.MovieAdapter
import com.example.netflixroulette.dagger.AppComponentProvider
import com.example.netflixroulette.viewModels.SearchViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import com.example.netflixroulette.views.support_views.MainContainerActivity
import kotlinx.android.synthetic.main.fragment_search_with.*


class SearchWithFragment : BaseFragment() {
    private val viewModel: SearchViewModel by viewModels()

    companion object {
        fun newInstance() = SearchWithFragment()

        const val SEARCH_WITH = "Search with "
        const val TITLE = "title"
        const val PERSON = "person"
        const val DEF_VALUE = "error :("
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponentProvider.provideAppComponent(requireContext()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search_with, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSerchHint()
        checkMenu()
        initAdapter()
        initObserver()
    }

    override fun onStop() {
        var inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)

        viewModel.scrollPosition =
            fragment_search_with_rv_movie_list.layoutManager?.onSaveInstanceState()

        super.onStop()
    }

    private fun initObserver() {
        viewModel.movies.removeObservers(this)
        viewModel.movies.observe(viewLifecycleOwner) {
            fragment_search_with_tv_label.visibility = View.GONE
            fragment_search_with_pb_load.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                fragment_search_with_tv_label_empty_results.visibility = View.VISIBLE
            }

            fragment_search_with_rv_movie_list.run {
                viewModel.scrollPosition = layoutManager?.onSaveInstanceState()
                adapter = MovieAdapter(it).apply {
                    layoutAnimation = AnimationUtils.loadLayoutAnimation(
                        context,
                        R.anim.layout_animation_from_bottom
                    )
                    scheduleLayoutAnimation()
                }

                layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)
            }
        }
    }

    private fun initAdapter() {
        fragment_search_with_rv_movie_list.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        fragment_search_with_rv_movie_list.layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)

        fragment_search_with_et_search.afterTextChangedDelayed {
            if (!it.isNullOrBlank()) {
                fragment_search_with_pb_load?.visibility = View.VISIBLE
                fragment_search_with_tv_label_empty_results.visibility = View.GONE
                if (arguments?.getString(SEARCH_WITH, DEF_VALUE) == TITLE) {
                    viewModel.handleSearchByTitle(it)
                } else {
                    viewModel.handleSearchByPerson(it)
                }
            }
        }
    }

    private fun setSerchHint() {
        fragment_search_with_et_search.hint =
            SEARCH_WITH + arguments?.getString(SEARCH_WITH, DEF_VALUE)
    }

    private fun TextView.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            var wasChange: Boolean = false
            var before: String? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                before = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                wasChange = true
            }

            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        if ((editable.toString().length == before?.length?.plus(1) ?: 0)
                            || (editable.toString().length == before?.length?.minus(1) ?: 0)
                        ) {

                            afterTextChanged.invoke(editable.toString())
                        }
                    }
                }.start()
            }
        })
    }

    private fun checkMenu() {
        if (arguments?.getString(SEARCH_WITH, DEF_VALUE) == TITLE) {
            (activity as MainContainerActivity).setNavItemChecked(1)
        } else {
            (activity as MainContainerActivity).setNavItemChecked(2)
        }
    }

}