package com.example.elearningmad.SingleCoursePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elearningmad.SingleCoursePage.Adapter.WeekAdapter
import com.example.elearningmad.SingleCoursePage.Model.WeekData
import com.google.firebase.database.*

class
ShowAllData : AppCompatActivity() {


    private lateinit var employeeItem: RecyclerView
    private lateinit var empList:ArrayList<WeekData>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_data)


        employeeItem=findViewById(R.id.recycleview)
        employeeItem.layoutManager= LinearLayoutManager(this,)
        employeeItem.setHasFixedSize(true)
        empList= arrayListOf<WeekData>()

        getEmployeeData()
    }

    private fun getEmployeeData(){
        employeeItem.visibility= View.GONE

        dbRef= FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData=empSnap.getValue(WeekData::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter= WeekAdapter(empList)

                    mAdapter.setOnItemClickListener(object:WeekAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@ShowAllData, UpdateDelete::class.java)
                            //put extras
                            intent.putExtra("title",empList[position].title)
                            intent.putExtra("des",empList[position].description)
                            intent.putExtra("Lw",empList[position].LastWeekLesson)
                            intent.putExtra("id",empList[position].Wid)
                            startActivity(intent)
                        }
                    })

                    employeeItem.adapter=mAdapter
                    employeeItem.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }

}
