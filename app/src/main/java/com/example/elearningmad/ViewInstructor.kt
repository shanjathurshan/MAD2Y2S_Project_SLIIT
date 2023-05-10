package com.example.elearningmad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elearningmad.Adapters.InstructorAdapter
import com.example.elearningmad.DataClasses.Instructor
import com.example.elearningmad.databinding.ActivityViewInstructorBinding
import com.google.firebase.database.*

class ViewInstructor : AppCompatActivity() {
    private lateinit var binding: ActivityViewInstructorBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var mList = ArrayList<Instructor>()
    private lateinit var adapter: InstructorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            intent = Intent(applicationContext, AddInstructor::class.java)
            startActivity(intent)
        }

        //initialize variables
        databaseRef = FirebaseDatabase.getInstance().reference.child("instructor")

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = InstructorAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: InstructorAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, EditInstructor::class.java).also {
                    it.putExtra("name", mList[position].name)
                    it.putExtra("email", mList[position].email)
                    it.putExtra("course", mList[position].course)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }
            }
        })

    }

    private fun retrieveData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val instructor = snapshot.getValue(Instructor::class.java)!!
                    if( instructor != null){
                        mList.add(instructor)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewInstructor, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}