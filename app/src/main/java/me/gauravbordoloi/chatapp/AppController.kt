package me.gauravbordoloi.chatapp

import android.app.Application
import me.gauravbordoloi.chatapp.util.SharedPref

class AppController: Application() {

    companion object {

        lateinit var sharedPref: SharedPref

    }

    override fun onCreate() {
        super.onCreate()

        sharedPref = SharedPref(applicationContext)

    }

}