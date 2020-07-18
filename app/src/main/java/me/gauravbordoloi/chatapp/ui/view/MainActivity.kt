package me.gauravbordoloi.chatapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val controller = NavHostFragment.findNavController(navHostFragment)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.userFragment) {
                toolbar.title = getString(R.string.app_name)
            } else {
                toolbar.title = "${getString(R.string.app_name)} - ${AppController.sharedPref.getUsername()}"
            }
        }

    }

}