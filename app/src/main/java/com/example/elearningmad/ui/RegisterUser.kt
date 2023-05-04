package com.example.elearningmad.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.Database.MyService
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterUser : AppCompatActivity() {
    
    private lateinit var binding : ActivityRegisterUserBinding
    private lateinit var  auth: FirebaseAuth

//    var username: EditText? = null
//    var email: EditText? = null
//    var password: EditText? = null
//    var uni: EditText? = null
//    var phone: EditText? = null

    var usernameId: EditText? = null
    var emailId: EditText? = null
    var passwordId: EditText? = null
    var phoneId: EditText? = null
    var uniId: EditText? = null

    var register: Button? = null
    var isAllFieldsChecked = false

    var MyService = MyService();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        var username = binding.editUsername
        var email = binding.editEmail
        var password = binding.editPassword
        var uni = binding.editUni
        var phone = binding.editPhone

        usernameId = findViewById(R.id.editUsername)
        emailId = findViewById(R.id.editEmail)
        passwordId = findViewById(R.id.editPassword)
        phoneId = findViewById(R.id.editPhone)
        uniId = findViewById(R.id.editUni)

        register = findViewById(R.id.register)
//        val loading = binding.loading

        register?.setOnClickListener {

            MyService.hideKeyboard((register)!!) // hide the keyboard

            isAllFieldsChecked = CheckAllFields()

            // the boolean variable turns to be true then
            if (isAllFieldsChecked) {

                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        MyService.createUser(username.text.toString(), email.text.toString(), password.text.toString(), uni.text.toString(), phone.text.toString());

                        Toast.makeText(applicationContext,"Successfully registered!",Toast.LENGTH_LONG).show();
                        val i = Intent(this, LoginUser::class.java)
                        startActivity(i)
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun CheckAllFields(): Boolean {
        if (usernameId!!.length() == 0) {
            usernameId!!.error = "This field is required"
            return false
        }
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