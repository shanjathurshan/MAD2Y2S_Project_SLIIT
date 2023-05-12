package com.example.elearningmad

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.databinding.ActivityMainBinding
import com.example.elearningmad.ui.InitialPage
import com.example.elearningmad.ui.InitialPageLecturer
import com.example.elearningmad.ui.LoginUser
import com.example.elearningmad.ui.RegisterUser
import com.example.elearningmad.ui.course.AddCourse
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
        //      Codes for SharedPreferences
        val sharedPref : SharedPreferences = applicationContext.getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
        val editor:  SharedPreferences.Editor = sharedPref.edit()

        var userId = sharedPref.getString("userId", "defaultname")
        var userType = sharedPref.getString("userType", "defaultname")

        Log.d(ContentValues.TAG, "userId - ${userId}")
        Log.d(ContentValues.TAG, "type - $userType")

        if(userId != "defaultname"){

            if(userType == "STUDENT"){
                val i = Intent(this, InitialPage::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, InitialPageLecturer::class.java)
                startActivity(i)
            }

        } else {
            val intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }



//        binding = ActivityMainBinding.inflate(layoutInflater)
//        supportActionBar?.hide();
//        setContentView(binding.root)

    }

    fun registerData(view: View) {
        val intent = Intent(this, RegisterUser::class.java)
        startActivity(intent)
    }



}