package com.example.elearningmad

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.databinding.ActivityMainBinding
import com.example.elearningmad.ui.InitialPage


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        getSupportActionBar()?.hide();
        setContentView(binding.root)

        val button = findViewById<Button>(R.id.button1)

        button.setOnClickListener {
            // Create an Intent object that specifies the activity to navigate to
            val intent = Intent(this, InitialPage::class.java)

            // Call the startActivity method and pass the Intent object as an argument
            startActivity(intent)
        }

//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_courses, R.id.navigation_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
}