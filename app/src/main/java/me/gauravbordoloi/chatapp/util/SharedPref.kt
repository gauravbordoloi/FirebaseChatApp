package me.gauravbordoloi.chatapp.util

import android.content.Context
import me.gauravbordoloi.chatapp.BuildConfig

class SharedPref(val context: Context) {

    private val USERNAME = "account_username"

    private val sharedPref =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun getUsername(): String {
        return sharedPref.getString(USERNAME, "")!!
    }

    fun setUsername(username: String) {
        editor.putString(USERNAME, username)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

}