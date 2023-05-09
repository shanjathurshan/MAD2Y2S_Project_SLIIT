package com.example.elearningmad

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDelete : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        dbRef=FirebaseDatabase.getInstance().reference.child("WeekData")

        val id= intent.getStringExtra("id").toString()
        val Title= intent.getStringExtra("title").toString()
        val Des= intent.getStringExtra("des").toString()
        val Lw= intent.getStringExtra("Lw").toString()

        var thisWelL=findViewById<EditText>(R.id.LthisWeek)
        var title=findViewById<EditText>(R.id.title)
        var des=findViewById<EditText>(R.id.description)
        var submit=findViewById<Button>(R.id.button)
        var Delete=findViewById<Button>(R.id.button2)

        thisWelL.setText(Lw)
        title.setText(Title)
        des.setText(Des)




        submit.setOnClickListener {

            var thisWeek=findViewById<EditText>(R.id.LthisWeek).text.toString()
            var thisTitle=findViewById<EditText>(R.id.title).text.toString()
            var thisDes =findViewById<EditText>(R.id.description).text.toString()


            update(thisWeek.toString(),thisDes.toString(),thisTitle.toString(),id)
        }

        Delete.setOnClickListener {
            Delete(id)
        }
    }

    fun Delete(id:String){

        dbRef.child(id).removeValue().addOnCompleteListener {
            Toast.makeText(this,"Data delete Success",Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, ShowAllData::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Data delete UnSuccess",Toast.LENGTH_SHORT).show()
        }
    }



    fun update(LastWeek:String,description:String,title:String,id:String){
        val updates= mapOf(
            "LastWeekLesson" to LastWeek,
            "description" to description,
            "title" to title
        )
        dbRef.child(id).updateChildren(updates).addOnCompleteListener {
            Toast.makeText(this,"Data Update Success",Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, ShowAllData::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"Data Update Unsuccess",Toast.LENGTH_SHORT).show()
        }
    }

}