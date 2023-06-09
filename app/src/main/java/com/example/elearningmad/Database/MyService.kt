package com.example.elearningmad.Database

import android.app.Service
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.ui.data.model.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyService : Service() {
    val db = Firebase.firestore // firestore DB

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun createUser(username: String, email:String, uni: String, phone: String, type: String ){
        // Add a new document with a generated ID
        db.collection("users")
            .add(Students(username, email, uni, phone, type))
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun hideKeyboard(view: View) {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        } catch (ignored: Exception) {
        }
    }

    fun checkFirebaseUser(){
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            Log.d(ContentValues.TAG, "User available!")
            Log.d(ContentValues.TAG,  user.uid)
        } else {
            Log.d(ContentValues.TAG, "User not available!")
        }
    }

    fun logout(){
        val sharedPref : SharedPreferences = applicationContext.getSharedPreferences("PREFERENCE_FILE_KEY",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor:  SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()

        var userId = sharedPref.getString("userId", "defaultname")
        var userType = sharedPref.getString("userType", "defaultname")

        Log.d(ContentValues.TAG, "userId - ${userId}")
        Log.d(ContentValues.TAG, "type - $userType")
    }

}