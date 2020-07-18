package me.gauravbordoloi.chatapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.R
import me.gauravbordoloi.chatapp.notification.NotificationUtil
import me.gauravbordoloi.chatapp.util.Const
import me.gauravbordoloi.chatapp.util.Helper
import java.util.*

class UserFragment : Fragment() {

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(200)
            if (AppController.sharedPref.getUsername().isNotEmpty()) {
                findNavController().navigate(UserFragmentDirections.actionUserFragmentToChatFragment())
            }
        }

        buttonContinue.setOnClickListener {
            val username =
                inputUsername.editText?.text.toString().trim().toLowerCase(Locale.getDefault())
            if (username == Const.USERNAME_1 || username == Const.USERNAME_2) {
                Helper.hideSoftKeyboard(it)
                NotificationUtil.updateToken()
                AppController.sharedPref.setUsername(username)
                findNavController().navigate(UserFragmentDirections.actionUserFragmentToChatFragment())
            } else {
                Snackbar.make(
                    it,
                    "Available username are : ${Const.USERNAME_1} , ${Const.USERNAME_2}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

    }

}