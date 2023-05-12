package com.example.elearningmad.ui.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class Students{
    var username: String = ""
    var email: String = ""
    var university: String = ""
    var phone: String = ""
    var type: String = ""

    constructor(a: String,b: String, d: String, e: String, f: String){
        username = a;
        email = b;
        university = d;
        phone = e;
        type = f;
    }
}