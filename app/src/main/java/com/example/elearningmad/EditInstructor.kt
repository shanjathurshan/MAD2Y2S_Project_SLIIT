package com.example.elearningmad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.elearningmad.databinding.ActivityEditInstructorBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditInstructor : AppCompatActivity() {
    private lateinit var binding: ActivityEditInstructorBinding
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fetch data from the intent
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val course = intent.getStringExtra("course")
        val id = intent.getStringExtra("id")

        //bind values to editTexts
        binding.edName.setText(name)
        binding.edEmail.setText(email)
        binding.edCourse.setText(course)

        databaseRef = FirebaseDatabase.getInstance().reference.child("instructor")

        binding.editBtn.setOnClickListener {
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
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["name"] = name
                map["email"] = email
                map["course"] = course



                //update database from hashMap
                databaseRef.child(id!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, ViewInstructor::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.deleteBtn.setOnClickListener {
            databaseRef.child(id!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, ViewInstructor::class.java)
                    startActivity(intent)
                }
            }
        }



    }
}