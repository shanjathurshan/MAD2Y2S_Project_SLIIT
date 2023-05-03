package com.example.elearningmad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.databinding.ActivityMainBinding
import com.example.elearningmad.ui.InitialPage
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        getSupportActionBar()?.hide();
        setContentView(binding.root)
//        setContentView(R.layout.activity_edit_profile)

//        // ACCESS THE BUTTON USING BY ID
        val button = findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            // Create an Intent object that specifies the activity to navigate to
            val intent = Intent(this, InitialPage::class.java)

            // Call the startActivity method and pass the Intent object as an argument
            startActivity(intent)
        }

    }

    fun sendData(view: View) {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
//
        myRef.setValue("Hello, World vishwa!")
    }

    fun sendDataFireStore(){

    }
}