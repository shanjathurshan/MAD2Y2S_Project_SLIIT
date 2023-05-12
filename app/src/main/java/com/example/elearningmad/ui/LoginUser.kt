package com.example.elearningmad.ui

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.elearningmad.Database.MyService
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityLoginUserBinding
import com.example.elearningmad.ui.data.model.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginUser : AppCompatActivity() {

    private lateinit var binding : ActivityLoginUserBinding
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var emailId: EditText? = null
    var passwordId: EditText? = null
    var login: Button? = null
    var isAllFieldsChecked = false
    var userType: String? = null

    var MyService = MyService();

    var signup: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance();

        var email = binding.editEmail
        var password = binding.editPassword

        emailId = findViewById(R.id.editEmail)
        passwordId = findViewById(R.id.editPassword)

//      Codes for SharedPreferences
        val sharedPref : SharedPreferences = applicationContext.getSharedPreferences("PREFERENCE_FILE_KEY", MODE_PRIVATE)
        val editor:  SharedPreferences.Editor = sharedPref.edit()

        login = findViewById(R.id.login)

        signup = findViewById(R.id.signup)

        // loginLink onclick
        signup?.setOnClickListener {
            val i = Intent(this, RegisterUser::class.java)
            startActivity(i)
        }

        login?.setOnClickListener {

            MyService.hideKeyboard((login)!!) // hide the keyboard
            login?.setVisibility(View.GONE)

            isAllFieldsChecked = CheckAllFields()

            // the boolean variable turns to be true then
            if (isAllFieldsChecked) {

                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener { task ->

                        if(task.isSuccessful){
                            //Another firebase query for get the userID and save to local storage!
                            db!!.collection("users")
                                .whereEqualTo("email", email.text.toString())
                                .get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        editor.putString("userId", document.id.toString())
                                        editor.putString("userType", document.data.get("type").toString())
                                        editor.apply()
                                        var userId = sharedPref.getString("userId", "defaultname")
                                        userType = sharedPref.getString("userType", "defaultname")

                                        Log.d(ContentValues.TAG, "userId - ${userId}")
                                        Log.d(ContentValues.TAG, "type - $userType")

                                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                                        // Navigate to home page.
                                        Toast.makeText(applicationContext,"Successfully logIn!", Toast.LENGTH_LONG).show();

                                        if(userType == "STUDENT"){
                                            val i = Intent(this, InitialPage::class.java)
                                            startActivity(i)
                                        } else {
                                            val i = Intent(this, InitialPageLecturer::class.java)
                                            startActivity(i)
                                        }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                                }

                            login?.setVisibility(View.VISIBLE)
                        }
                }.addOnFailureListener { exception ->
                        login?.setVisibility(View.VISIBLE)
                        Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }
        }


    }

    private fun CheckAllFields(): Boolean {
        if (emailId!!.length() == 0) {
            emailId!!.error = "Email is required"
            return false
        }
        if (passwordId!!.length() == 0) {
            passwordId!!.error = "Password is required"
            return false
        } else if (passwordId!!.length() < 5) {
            passwordId!!.error = "Password must be minimum 5 characters"
            return false
        }

        // after all validation return true.
        return true
    }
}