package com.example.netflixroulette.ui.searchWith

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixroulette.R
import com.example.netflixroulette.databinding.FragmentSearchWithBinding
import com.example.netflixroulette.helpers.ErrorHandler
import com.example.netflixroulette.ui.MainContainerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchWithFragment : Fragment() {
    private var _binding: FragmentSearchWithBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel for handle search
     */
    private val viewModel: SearchViewModel by viewModels()

    /**
     * Entity that insulate errors codes and display error message base on code [ErrorHandler.showError]
     */
    @Inject
    lateinit var errorHandler: ErrorHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchWithBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        viewModel.scrollPosition = binding.fragmentSearchWithRvMovieList.layoutManager?.onSaveInstanceState()

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
        binding.fragmentSearchWithEtSearch.hint = SEARCH_WITH + arguments?.getString(SEARCH_WITH, DEF_VALUE)
    }

    /**
     * Layout manager initialization depends of devise orientation and also we restoring position of items list
     * and also set text changed listener with delay [afterTextChangedDelayed].
     *
     */
    private fun initLayoutManager() {
        binding.fragmentSearchWithRvMovieList.layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        binding.fragmentSearchWithRvMovieList.layoutManager?.onRestoreInstanceState(viewModel.scrollPosition)

        binding.fragmentSearchWithEtSearch.afterTextChangedDelayed {
            if (it.isNotBlank()) {
                binding.fragmentSearchWithPbLoad.visibility = View.VISIBLE
                binding.fragmentSearchWithTvLabelEmptyResults.visibility = View.GONE
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
        viewModel.error.observe(this) {
            errorHandler.showError(requireContext(), it)
        }

        viewModel.movies.removeObservers(this)
        viewModel.movies.observe(this) {
            binding.fragmentSearchWithTvLabel.visibility = View.GONE
            binding.fragmentSearchWithPbLoad.visibility = View.GONE
            if (it.isNullOrEmpty()) {
                binding.fragmentSearchWithTvLabelEmptyResults.visibility = View.VISIBLE
            }

            binding.fragmentSearchWithRvMovieList.run {
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

    companion object {
        fun newInstance() = SearchWithFragment()

        const val SEARCH_WITH = "Search with "
        const val TITLE = "title"
        const val PERSON = "person"
        const val DEF_VALUE = "error :("
    }
}