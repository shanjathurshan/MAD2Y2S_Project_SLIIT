package com.example.elearningmad.ui.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class Students{
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var university: String = ""
    var phone: String = ""

    constructor(a: String,b: String,c: String, d: String, e: String){
        username = a;
        email = b;
        password = c;
        university = d;
        phone = e;
    }
}