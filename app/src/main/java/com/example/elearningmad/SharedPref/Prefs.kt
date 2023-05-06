package com.example.elearningmad.SharedPref

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context)
{
    private var APP_PREF_INT_EXAMPLE = "intExamplePref";
    private var APP_PREF_STRING_EXAMPLE = "strExamplePref";

    private val preferences: SharedPreferences = context.getSharedPreferences(APP_PREF_INT_EXAMPLE, Context.MODE_PRIVATE);
    private val preferences1: SharedPreferences = context.getSharedPreferences(APP_PREF_STRING_EXAMPLE, Context.MODE_PRIVATE);

//    var intPref: Int
//        get() = preferences.getInt(APP_PREF_INT_EXAMPLE, -1)
//        set(value) = preferences.edit().putInt(APP_PREF_INT_EXAMPLE, value).apply()

    var stringPref: String
        get() = preferences1.getString(APP_PREF_STRING_EXAMPLE, "")!!
        set(value) = preferences1.edit().putString(APP_PREF_STRING_EXAMPLE, value).apply()
}