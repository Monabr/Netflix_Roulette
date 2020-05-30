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
import com.example.netflixroulette.adapters.SearchedMovieAdapter
import com.example.netflixroulette.helpers.ErrorHandler
import com.example.netflixroulette.viewModels.SearchViewModel
import com.example.netflixroulette.views.support_views.BaseFragment
import com.example.netflixroulette.views.support_views.MainContainerActivity
import kotlinx.android.synthetic.main.fragment_search_with.*
import javax.inject.Inject


class SearchWithFragment : BaseFragment() {

    /**
     * ViewModel for handle search
     */
    private val viewModel: SearchViewModel by viewModels()

    /**
     * Entity that insulate errors codes and display error message base on code [ErrorHandler.showError]
     */
    @Inject
    lateinit var errorHandler: ErrorHandler

    companion object {
        fun newInstance() = SearchWithFragment()

        const val SEARCH_WITH = "Search with "
        const val TITLE = "title"
        const val PERSON = "person"
        const val DEF_VALUE = "error :("
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainContainerActivity).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search_with, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkMenu()
        setSearchHint()
        initLayoutManager()
        initObservers()
    }

    /**
     * This event is using for
     * - hide keyboard when we leave the fragment
     * - save position of items list when we leave the fragment
     *
     */
    override fun onStop() {
        val inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)

        viewModel.scrollPosition =
            fragment_search_with_rv_movie_list.layoutManager?.onSaveInstanceState()

        super.onStop()
    }

    /**
     * Simply checking menu item because we enter to this fragment for search with title or author
     *
     */
    private fun checkMenu() {
        if (arguments?.getString(SEARCH_WITH, DEF_VALUE) == TITLE) {
            (activity as MainContainerActivity).setNavItemChecked(1)
        } else {
            (activity as MainContainerActivity).setNavItemChecked(2)
        }
    }

    /**
     * Set search hint depends on category we search for (title or author)
     *
     */
    private fun setSearchHint() {
        fragment_search_with_et_search.hint =
            SEARCH_WITH + arguments?.getString(SEARCH_WITH, DEF_VALUE)
    }

    /**
     * Layout manager initialization depends of devise orientation and also we restoring position of items list
     * and also set text changed listener with delay [afterTextChangedDelayed].
     *
     */
    private fun initLayoutManager() {
        fragment_search_with_rv_movie_list.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        fragment_search_with_rv_movie_list.layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)

        fragment_search_with_et_search.afterTextChangedDelayed {
            if (!it.isBlank()) {
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

    /**
     * Observer initialization for getting data and using mechanism of saving and restore position of items list to prevent flickering
     *
     */
    private fun initObservers() {

        viewModel.error.removeObservers(this)
        viewModel.error.observe(viewLifecycleOwner) {
            errorHandler.showError(it)
        }

        viewModel.movies.removeObservers(this)
        viewModel.movies.observe(viewLifecycleOwner) {
            fragment_search_with_tv_label.visibility = View.GONE
            fragment_search_with_pb_load.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                fragment_search_with_tv_label_empty_results.visibility = View.VISIBLE
            }

            fragment_search_with_rv_movie_list.run {
                viewModel.scrollPosition = layoutManager?.onSaveInstanceState()
                adapter = SearchedMovieAdapter(it).apply {
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

    /**
     * Set listener of text changing with delay and do something with [String] that will come
     *
     * @param afterTextChanged function that describe how to handle [String] that will come
     */
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
}