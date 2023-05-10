package com.example.elearningmad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.elearningmad.DataClasses.Instructor
import com.example.elearningmad.databinding.ActivityAddInstructorBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddInstructor : AppCompatActivity() {
    private lateinit var binding : ActivityAddInstructorBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("instructor")

        binding.addBtn.setOnClickListener {

            var name = binding.edName.text.toString()
            var email = binding.edEmail.text.toString()
            var course = binding.edCourse.text.toString()

            //validate form
            if(name.isEmpty() || email.isEmpty() || course.isEmpty()){

                if(name.isEmpty()){
                    binding.edName.error = "Enter Name"
                }
                if(email.isEmpty()){
                    binding.edEmail.error = "Enter Email"
                }
                if(course.isEmpty()){
                    binding.edCourse.error = "Enter Course"
                }
            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val request = Instructor(name, email, course,id)
                databaseRef.child(id).setValue(request).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, ViewInstructor::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Your requests added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}