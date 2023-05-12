package com.example.elearningmad.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.elearningmad.Database.MyService
import com.example.elearningmad.R
import com.example.elearningmad.databinding.ActivityRegisterUserBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterUser : AppCompatActivity() {
    
    private lateinit var binding : ActivityRegisterUserBinding
    private lateinit var  auth: FirebaseAuth

    var usernameId: EditText? = null
    var emailId: EditText? = null
    var passwordId: EditText? = null
    var phoneId: EditText? = null
    var uniId: EditText? = null
    var typeId: String? = null
    val checked = null

    var genderradioButton: RadioButton? = null
    var radioGroup: RadioGroup? = null

    var register: Button? = null
    var loginLink: TextView? = null
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
        radioGroup= findViewById(R.id.radioGroup);

        register = findViewById(R.id.register)
        loginLink = findViewById(R.id.loginLink)

        // Register button onClick
        register?.setOnClickListener {

            radioValidation(); // radio Validation

            MyService.hideKeyboard((register)!!) // hide the keyboard
            isAllFieldsChecked = CheckAllFields()
            // the boolean variable turns to be true then
            if (isAllFieldsChecked) {
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        register?.setVisibility(View.VISIBLE)
                        MyService.createUser(username.text.toString(), email.text.toString(), uni.text.toString(), phone.text.toString(), typeId.toString());
                        Toast.makeText(applicationContext,"Successfully registered!",Toast.LENGTH_LONG).show();
                        val i = Intent(this, LoginUser::class.java)
                        startActivity(i)
                    }
                }.addOnFailureListener { exception ->
                    register?.setVisibility(View.VISIBLE)
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

        // loginLink onclick
        loginLink?.setOnClickListener {
            val i = Intent(this, LoginUser::class.java)
            startActivity(i)
        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {

            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.user_student ->
                    if (checked) {
                        typeId = "STUDENT"
                        Log.d(ContentValues.TAG, "user_student")
                    }
                R.id.user_lecturer ->
                    if (checked) {
                        typeId = "LECTURER"
                        Log.d(ContentValues.TAG, "user_lecturer")
                    }
            }
        }
    }

    fun radioValidation(){
        val selectedId = radioGroup?.getCheckedRadioButtonId()
        genderradioButton = findViewById(selectedId!!);

        if(selectedId==-1){
            Toast.makeText(applicationContext,"Select user type!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(applicationContext,genderradioButton?.getText(), Toast.LENGTH_SHORT).show();
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