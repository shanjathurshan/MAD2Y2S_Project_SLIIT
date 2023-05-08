package com.example.elearningmad.ui.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elearningmad.CourseModel
import com.example.elearningmad.MainActivity
import com.example.elearningmad.R
import com.example.elearningmad.ui.InitialPage
import com.example.elearningmad.ui.dashboard.CoursesViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddCourse : AppCompatActivity() {

    private lateinit var courseName : EditText
    private lateinit var courseId : EditText
    private lateinit var courseDuration : EditText
    private lateinit var courseInstructor : EditText
    private lateinit var courseDescription : EditText
    private lateinit var courseFees : EditText
    private lateinit var courseUrl : EditText
    private lateinit var saveData : Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)


        courseName = findViewById(R.id.editTextTextPersonName8)
        courseId = findViewById(R.id.editTextTextPersonName8)
        courseDuration = findViewById(R.id.editTextTextPersonName)
        courseInstructor = findViewById(R.id.editTextTextPersonName2)
        courseDescription = findViewById(R.id.textInputEditText)
        courseUrl = findViewById(R.id.editTextTextPersonName3)
        saveData = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("Courses")

        saveData.setOnClickListener{
            saveCourseData()
        }
    }
    private fun saveCourseData() {
        //getting values
        val cName = courseName.text.toString()
        val cId = courseId.text.toString()
        val cDuration = courseDuration.text.toString()
        val cInstructor = courseInstructor.text.toString()
        val cDescription = courseDescription.text.toString()
        val cUrl = courseUrl.text.toString()

        if(cName.isEmpty()) {
            courseName.error = "Please enter the course Name"
        }
        if(cId.isEmpty()) {
            courseId.error = "Please enter the course ID"
        }
        if(cDuration.isEmpty()) {
            courseDuration.error = "Please enter the course Duration"
        }
        if(cInstructor.isEmpty()) {
            courseInstructor.error = "Please enter the Instructor name"
        }
        if(cDescription.isEmpty()) {
            courseDescription.error = "Please enter the course Description"
        }
        if(cUrl.isEmpty()) {
            courseUrl.error = "Please enter the course URL"
        }

        val uniqueCid = dbRef.push().key!!

        val course = CourseModel(uniqueCid, cName, cId, cDuration, cInstructor, cDescription, cUrl)

        dbRef.child(uniqueCid).setValue(course).addOnCompleteListener{
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()

            courseName.text.clear()
            courseId.text.clear()
            courseDuration.text.clear()
            courseInstructor.text.clear()
            courseDescription.text.clear()
            courseUrl.text.clear()


        }.addOnFailureListener{ err->
            Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun createCourseToInitialPage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun goToSeeAllCourses(view: View) {
        val intent = Intent(this, Activity_fetching::class.java)
        startActivity(intent)
    }

}