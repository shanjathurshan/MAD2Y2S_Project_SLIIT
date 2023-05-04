package com.example.elearningmad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elearningmad.Database.MyService
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityLoginUserBinding
import com.google.firebase.auth.FirebaseAuth

class LoginUser : AppCompatActivity() {

    private lateinit var binding : ActivityLoginUserBinding
    private lateinit var  auth: FirebaseAuth

    var emailId: EditText? = null
    var passwordId: EditText? = null
    var login: Button? = null
    var isAllFieldsChecked = false

    var MyService = MyService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        var email = binding.editEmail
        var password = binding.editPassword

        emailId = findViewById(R.id.editEmail)
        passwordId = findViewById(R.id.editPassword)

        login = findViewById(R.id.login)

        login?.setOnClickListener {

            MyService.hideKeyboard((login)!!) // hide the keyboard

            isAllFieldsChecked = CheckAllFields()

            // the boolean variable turns to be true then
            if (isAllFieldsChecked) {

                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Successfully logIn!", Toast.LENGTH_LONG).show();
                        val i = Intent(this, InitialPage::class.java)
                        startActivity(i)
                    }
                }.addOnFailureListener { exception ->
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