package com.example.elearningmad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elearningmad.SingleCoursePage.Model.WeekData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddRewiev : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rewiev)

        dbRef=FirebaseDatabase.getInstance().getReference("WeekData")
        val add=findViewById<Button>(R.id.btnAdd)



        add.setOnClickListener {
            AddData()
        }

    }

    fun AddData(){
        val des=findViewById<EditText>(R.id.Description)
        val title=findViewById<EditText>(R.id.Title)
        val lastWeekLesson=findViewById<EditText>(R.id.Lessonweek)

        val Des=des.text.toString()
        val Title=title.text.toString()
        val LastWeekLesson=lastWeekLesson.text.toString()

        val key=dbRef.push().key!!
        val addData=WeekData(Title,Des, LastWeekLesson,key)

        dbRef.child(key).setValue(addData).addOnCompleteListener {
            Toast.makeText(this,"Data added",Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, ShowAllData::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Data added error",Toast.LENGTH_SHORT).show()
        }
    }
}