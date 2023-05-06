package com.example.elearningmad.SingleCoursePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elearningmad.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDelete : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        dbRef=FirebaseDatabase.getInstance().getReference()

        val id= intent.getStringExtra("id").toString()
        val Title= intent.getStringExtra("title").toString()
        val Des= intent.getStringExtra("des").toString()
        val Lw= intent.getStringExtra("Lw").toString()

        val thisWelL=findViewById<EditText>(R.id.LthisWeek)
        val title=findViewById<EditText>(R.id.title)
        val des=findViewById<EditText>(R.id.description)
        val submit=findViewById<Button>(R.id.button)
        val Delete=findViewById<Button>(R.id.button2)

        thisWelL.setText(Lw)
        title.setText(Title)
        des.setText(Des)


        submit.setOnClickListener {
            update(thisWelL.toString(),des.toString(),title.toString(),id)
        }

        Delete.setOnClickListener {
            Delete(id)
        }
    }

    fun Delete(id:String){

        dbRef.child(id).removeValue().addOnCompleteListener {
            Toast.makeText(this,"Data delete Success",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Data delete UnSuccess",Toast.LENGTH_SHORT).show()
        }
    }



    fun update(LastWeek:String,description:String,title:String,id:String){

        val updates= mapOf(
            "LastWeek" to LastWeek,
            "description" to description,
            "title" to title
        )
        dbRef.child(id).updateChildren(updates).addOnCompleteListener {
            Toast.makeText(this,"Data Update Success",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Data Update Unsuccess",Toast.LENGTH_SHORT).show()
        }
    }

}