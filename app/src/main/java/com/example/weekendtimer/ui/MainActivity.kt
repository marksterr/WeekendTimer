package com.example.weekendtimer.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weekendtimer.R
import com.example.weekendtimer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_categories, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Add OnDestinationChangedListener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_timer -> {
                    navView.visibility = View.GONE
                    binding.container.setPadding(0, 0, 0, 0) // remove padding
                }
                else -> {
                    navView.visibility = View.VISIBLE
                    val paddingTop = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                            TypedValue.COMPLEX_UNIT_DIP.toFloat(),
                            resources.displayMetrics).toInt().toFloat(),
                        resources.displayMetrics
                    )
                    binding.container.setPadding(0, paddingTop.toInt(), 0, 0) // add padding
                }
            }
        }
    }
}