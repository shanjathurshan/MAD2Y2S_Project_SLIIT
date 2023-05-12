package com.example.elearningmad.ui

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.AddInstructor
import com.example.elearningmad.AddRewiev
import com.example.elearningmad.Database.MyService
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityInitalPageLecturerBinding
import com.example.elearningmad.ui.course.AddCourse

class InitialPageLecturer : AppCompatActivity() {

    private lateinit var binding: ActivityInitalPageLecturerBinding
    var MyService = MyService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitalPageLecturerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var button9: Button = findViewById(R.id.button9)

        button9.setOnClickListener {
            //      Codes for SharedPreferences
            val sharedPref : SharedPreferences = applicationContext.getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
            val editor:  SharedPreferences.Editor = sharedPref.edit()
            editor.clear()
            editor.commit()

            var userId = sharedPref.getString("userId", "defaultname")
            var userType = sharedPref.getString("userType", "defaultname")

            Log.d(ContentValues.TAG, "userId - ${userId}")
            Log.d(ContentValues.TAG, "type - $userType")

            val intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }


    }

    fun goToCreateCourses(view: View) {
        val intent = Intent(this, AddCourse::class.java)
        startActivity(intent)
    }

    fun goToSingleCourseCreate(view: View) {
        val intent = Intent(this, AddRewiev::class.java)
        startActivity(intent)
    }

    fun goToInstructor(view: View) {
        val intent = Intent(this, AddInstructor::class.java)
        startActivity(intent)
    }
}