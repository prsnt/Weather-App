package com.prashant.weatherapp.ui.home

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.prashant.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        toolbar.setNavigationOnClickListener {
            findNavController(R.id.nav_host_fragment).navigateUp()
        }
    }

    fun changeName(title: Int) {
        toolbar.setTitle(title)
    }

    fun changeName(title: String) {
        toolbar.setTitle(title)
    }

    fun removeBackButton() {
        toolbar.navigationIcon = null
    }

    fun addBackButton() {
        toolbar.navigationIcon = getDrawable(R.drawable.ic_back)
    }

    fun removeToolbar() {
        toolbar.visibility = View.GONE
    }

    fun addToolbar() {
        toolbar.visibility = View.VISIBLE
    }
}