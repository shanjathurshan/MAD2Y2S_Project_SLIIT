package com.example.elearningmad.ui

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.R
import com.example.elearningmad.ui.Profile.ProfileFragment
import com.example.elearningmad.ui.data.model.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EditProfile : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val intent = intent

        var usernameEdit = findViewById<EditText>(R.id.usernameEdit)
        var phoneEdit = findViewById<EditText>(R.id.phoneEdit)
        var emailEdit = findViewById<EditText>(R.id.emailEdit)
        var universityEdit = findViewById<EditText>(R.id.universityEdit)

        usernameEdit.setText(intent.getStringExtra("username"))
        emailEdit.setText(intent.getStringExtra("email"))
        phoneEdit.setText(intent.getStringExtra("phone"))
        universityEdit.setText(intent.getStringExtra("university"))

        db = FirebaseFirestore.getInstance();
        var user = FirebaseAuth.getInstance().currentUser

//        SharedPreferences
        val sharedPref : SharedPreferences = applicationContext.getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
        val editor:  SharedPreferences.Editor = sharedPref.edit()
        var userId = sharedPref.getString("userId", "defaultname")

        val update: Button = findViewById(R.id.update)

        update.setOnClickListener {
            update?.setVisibility(View.GONE)
            db!!.collection("users")
                .document(userId!!)
                .update(mapOf(
                    "username" to usernameEdit.text.toString(),
                    "email" to emailEdit.text.toString(),
                    "phone" to phoneEdit.text.toString(),
                    "university" to universityEdit.text.toString(),
                )).addOnCompleteListener {
                    val intent = Intent(this, InitialPage::class.java)
                    startActivity(intent)
                    Log.d(ContentValues.TAG, "Student updated!")
                }

//            need to add the loader and navigate the page
            update?.setVisibility(View.VISIBLE)

        }



    }
}