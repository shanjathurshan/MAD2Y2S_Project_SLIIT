package com.example.elearningmad.ui.course

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningmad.CourseModel
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityFetchingBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Activity_fetching : AppCompatActivity() {

    private lateinit var courseRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView

    private lateinit var courseList: ArrayList<CourseModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        courseRecyclerView = findViewById(R.id.tvLoadingData_1) // Corrected ID
        courseRecyclerView.layoutManager = LinearLayoutManager(this)
        courseRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)



        courseList = arrayListOf<CourseModel>()

        getCourseData()
    }

    private fun getCourseData() {
        courseRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Courses")

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                if(snapshot.exists()) {
                    for(courseSnap in snapshot.children) {
                        val courseData = courseSnap.getValue(CourseModel::class.java)
                        courseList.add(courseData!!)
                    }

                    val mAdapter = CourseAdapter(courseList)
                    courseRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : CourseAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@Activity_fetching, Course_details::class.java)

                            //put extras
                            intent.putExtra("uniqueCid",courseList[position].uniqueCid)
                            intent.putExtra("cName", courseList[position].cName)
                            intent.putExtra("cId", courseList[position].cId)
                            intent.putExtra("cDuration", courseList[position].cDuration)
                            intent.putExtra("cInstructor", courseList[position].cInstructor)
                            intent.putExtra("cDescription", courseList[position].cDescription)
                            intent.putExtra("cUrl", courseList[position].cUrl)
                            startActivity(intent)
                        }

                    })

                    courseRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            })
        }
}