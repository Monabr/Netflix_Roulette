package com.example.netflixroulette.views.support_views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.netflixroulette.R
import com.example.netflixroulette.views.SearchWithFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_container.*

/**
 * Main container for all application fragments since it's single activity application
 */
class MainContainerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        setSupportActionBar(activity_main_container_toolbar)

        var toggle = ActionBarDrawerToggle(
            this, drawer_layout, activity_main_container_toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        activity_main_container_nv_navigation.setNavigationItemSelectedListener(this)
    }

    /**
     * It will just close left menu when you press back button
     *
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.nav_saved && !item.isChecked ->
                findNavController(R.id.container).navigate(R.id.SavedMoviesFragment)

            item.itemId == R.id.nav_search_with_title && !item.isChecked -> findNavController(R.id.container).navigate(
                R.id.searchWithFragment,
                Bundle().apply {
                    putString(SearchWithFragment.SEARCH_WITH, SearchWithFragment.TITLE)
                })
            item.itemId == R.id.nav_search_with_person && !item.isChecked -> findNavController(R.id.container).navigate(
                R.id.searchWithFragment,
                Bundle().apply {
                    putString(SearchWithFragment.SEARCH_WITH, SearchWithFragment.PERSON)
                })
        }
        drawer_layout.closeDrawer(GravityCompat.START)
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
        activity_main_container_nv_navigation.menu.getItem(id).isChecked = true
    }

}
