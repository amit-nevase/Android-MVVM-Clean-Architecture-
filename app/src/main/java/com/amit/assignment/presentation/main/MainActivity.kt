package com.amit.assignment.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.amit.assignment.R
import com.amit.assignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Custom toolbar setup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.activity_characters_list_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    supportActionBar?.hide()
                    mainBinding.appBar.visibility = View.GONE
                }
                else -> {
                    supportActionBar?.show()
                    mainBinding.appBar.visibility = View.VISIBLE
                }
            }
        }

        // App bar config for toolbar visibility
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.splashFragment, R.id.charactersListFragment)
            .build()

        // Make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    // for toolbar title from other child screens
    fun setToolBarTitle(title: String?) {
        mainBinding.toolbarTitle.text = title
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
