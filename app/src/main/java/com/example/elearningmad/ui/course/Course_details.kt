package com.example.elearningmad.ui.course

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.CourseModel
import com.example.elearningmad.R
import com.google.firebase.database.FirebaseDatabase

class Course_details : AppCompatActivity() {

    private lateinit var tvUniqueCid: TextView
    private lateinit var tvCname: TextView
    private lateinit var tvCid: TextView
    private lateinit var tvCduration: TextView
    private lateinit var tvCinstructor: TextView
    private lateinit var tvCdescription: TextView
    private lateinit var tvCUrl: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("uniqueCid").toString(),
                intent.getStringExtra("cName").toString(),
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("uniqueCid").toString(),
            )
        }

        tvCUrl.setOnClickListener {
            val url = tvCUrl.text.toString()
            openUrl(url)
        }

    }

    private fun deleteRecord(
        uid: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Courses").child(uid)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this , "Course data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Activity_fetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this , "Deleting error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvUniqueCid = findViewById(R.id.tvUniqueCid)
        tvCname = findViewById(R.id.tvcName)
        tvCid = findViewById(R.id.tvcId2)
        tvCduration = findViewById(R.id.tvcDuration)
        tvCinstructor = findViewById(R.id.tvcInstructor)
        tvCdescription = findViewById(R.id.tvcDescription)
        tvCUrl = findViewById(R.id.tvcUrl)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvUniqueCid.text = intent.getStringExtra("uniqueCid")
        tvCname.text = intent.getStringExtra("cId")
        tvCid.text = intent.getStringExtra("cName")
        tvCduration.text = intent.getStringExtra("cDuration")
        tvCdescription.text = intent.getStringExtra("cDescription")
        tvCinstructor.text = intent.getStringExtra("cInstructor")
        tvCUrl.text = intent.getStringExtra("cUrl")
    }

    private fun openUpdateDialog(
        uniqueCid: String,
        cName: String,

        ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_dialog,null)

        mDialog.setView(mDialogView)

        val etCourseName = mDialogView.findViewById<EditText>(R.id.etCName)
        val etCourseId = mDialogView.findViewById<EditText>(R.id.etCId)
        val etCourseDuration = mDialogView.findViewById<EditText>(R.id.etCDuration)
        val etCourseInstructor = mDialogView.findViewById<EditText>(R.id.etCinstructor)
        val etCourseDescription = mDialogView.findViewById<EditText>(R.id.etCDescription)
        val etCourseUrl = mDialogView.findViewById<EditText>(R.id.etCurl)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCourseName.setText(intent.getStringExtra("cName").toString())
        etCourseId.setText(intent.getStringExtra("cId").toString())
        etCourseDuration.setText(intent.getStringExtra("cDuration").toString())
        etCourseInstructor.setText(intent.getStringExtra("cInstructor").toString())
        etCourseDescription.setText(intent.getStringExtra("cDescription").toString())
        etCourseUrl.setText(intent.getStringExtra("cUrl").toString())

        mDialog.setTitle("Updating $cName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateCourseData(
                uniqueCid,
                etCourseName.text.toString(),
                etCourseId.text.toString(),
                etCourseDuration.text.toString(),
                etCourseInstructor.text.toString(),
                etCourseDescription.text.toString(),
                etCourseUrl.text.toString()
            )

            Toast.makeText(applicationContext, "Course Data Updated", Toast.LENGTH_LONG).show()

            //setting updated data to text views

            tvCname.text = etCourseName.text.toString()
            tvCid.text = etCourseId.text.toString()
            tvCduration.text = etCourseDuration.text.toString()
            tvCdescription.text = etCourseDescription.text.toString()
            tvCinstructor.text = etCourseInstructor.text.toString()
            tvCUrl.text = etCourseUrl.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateCourseData(
        uid:String,
        name:String,
        id:String,
        duration:String,
        instructor:String,
        description:String,
        url:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Courses").child(uid)
        val courseInfo = CourseModel(uid,name, id, duration, instructor, description, url)
        dbRef.setValue(courseInfo)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }


}