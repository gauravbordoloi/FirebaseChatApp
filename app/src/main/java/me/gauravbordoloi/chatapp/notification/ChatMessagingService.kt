package me.gauravbordoloi.chatapp.notification

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.ui.view.MainActivity

class ChatMessagingService : FirebaseMessagingService() {

    private val TAG = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val data = remoteMessage.data
        Log.e(TAG, remoteMessage.data.toString())

        if (data.isNotEmpty()) {
            val resultIntent = Intent(this, MainActivity::class.java)
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            NotificationUtil.showNotification(this, data, resultPendingIntent)
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        if (AppController.sharedPref.getUsername().isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                NotificationUtil.updateToken(token)
            }
        }

    }

}