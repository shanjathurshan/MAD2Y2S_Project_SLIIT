package com.example.elearningmad

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.databinding.ActivityMainBinding
import com.example.elearningmad.ui.InitialPage
import com.example.elearningmad.ui.RegisterUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference // real time dB
    val db = Firebase.firestore // firestore DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        getSupportActionBar()?.hide();
        setContentView(binding.root)
//        setContentView(R.layout.activity_edit_profile)

//        // ACCESS THE BUTTON USING BY ID
        val button = findViewById<Button>(R.id.button2)
        val Instbutton = findViewById<Button>(R.id.button6)

        button.setOnClickListener {
            // Create an Intent object that specifies the activity to navigate to
            val intent = Intent(this, InitialPage::class.java)

            // Call the startActivity method and pass the Intent object as an argument
            startActivity(intent)
        }

        Instbutton.setOnClickListener {
            val intent = Intent(this, AddInstructor::class.java)

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

    fun sendDataFireStore(view: View){

        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815,
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun registerData(view: View) {
        val intent = Intent(this, RegisterUser::class.java)

        // Call the startActivity method and pass the Intent object as an argument
        startActivity(intent)
    }

}