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

// The @AndroidEntryPoint annotation tells Hilt this activity needs to participate in dependency injection.
@AndroidEntryPoint
// This is the main activity of your application. All activities are subclasses of the AppCompatActivity.
class MainActivity : AppCompatActivity() {

    // This is a reference to the binding object that will be used to access views in the layout for this activity.
    private lateinit var binding: ActivityMainBinding

    // The onCreate function is called when the activity is first created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the content view of this activity. The 'root' view in the binding object is used.
        setContentView(binding.root)

        // Here we get a reference to the BottomNavigationView.
        val navView: BottomNavigationView = binding.navView

        // Get the NavController, which controls navigation within the nav_host_fragment.
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Here we're creating an AppBarConfiguration, which will be used for configuring the ActionBar.
        // We are providing the id's of the top-level destinations of our app.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_categories
            )
        )
        // This connects the ActionBar and the NavController, making the ActionBar aware of the NavController.
        setupActionBarWithNavController(navController, appBarConfiguration)
        // This connects the BottomNavigationView and the NavController. It will update the selected item in the BottomNavigationView when the destination changes.
        navView.setupWithNavController(navController)

        // Here we're adding an OnDestinationChangedListener which is called whenever the current destination in the NavController changes.
        // We are hiding or showing the BottomNavigationView depending on the current destination.
        // We're also adjusting the padding of the 'container' view.
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
