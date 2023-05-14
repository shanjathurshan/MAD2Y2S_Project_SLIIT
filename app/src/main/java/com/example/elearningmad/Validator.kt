package com.example.elearningmad

object Validator {
    fun loginValidateInput(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

    fun registerValidateInput(email: String, password: String): Boolean {
        return !(email.isEmpty() || password.isEmpty())
    }

}