package com.example.netflixroulette.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.netflixroulette.R
import com.example.netflixroulette.databinding.ActivityMainContainerBinding
import com.example.netflixroulette.ui.searchWith.SearchWithFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main container for all application fragments since it's single activity application
 */

@AndroidEntryPoint
class MainContainerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityMainContainerToolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.activityMainContainerToolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.activityMainContainerNvNavigation.setNavigationItemSelectedListener(this)
    }

    /**
     * It will just close left menu when you press back button
     *
     */
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(!item.isChecked) {
            when (item.itemId) {
                R.id.nav_saved ->
                    findNavController(R.id.container).navigate(R.id.SavedMoviesFragment)

                R.id.nav_search_with_title -> findNavController(R.id.container).navigate(
                    R.id.searchWithFragment,
                    Bundle().apply {
                        putString(SearchWithFragment.SEARCH_WITH, SearchWithFragment.TITLE)
                    })
                R.id.nav_search_with_person -> findNavController(R.id.container).navigate(
                    R.id.searchWithFragment,
                    Bundle().apply {
                        putString(SearchWithFragment.SEARCH_WITH, SearchWithFragment.PERSON)
                    })
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Use it to check menu item when you change fragments.
     *
     * No need to uncheck items there is always only one can be the chosen one (if you check the new one the old one will be unchecked)
     *
     * @param id id item that should be checked
     */
    fun setNavItemChecked(id: Int) {
        binding.activityMainContainerNvNavigation.menu.getItem(id).isChecked = true
    }

}
