package me.gauravbordoloi.chatapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.R
import me.gauravbordoloi.chatapp.util.Const

object NotificationUtil {

    private val TAG = javaClass.simpleName
    const val CHANNEL_ID = Const.CHANNEL_ID

    fun showNotification(
        context: Context,
        data: Map<String, String>,
        pendingIntent: PendingIntent?
    ) {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
        } else {
            ""
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_bell)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context): String {
        val chan = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = context.getColor(R.color.colorPrimary)
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return CHANNEL_ID
    }

    private fun getToken(success: (String) -> Unit) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.e(TAG, "getToken", it.exception)
                }
                success(it.result?.token!!)
            }
    }

    fun updateToken() {
        GlobalScope.launch(Dispatchers.IO) {
            getToken { token ->
                updateToken(token)
            }
        }
    }

    fun updateToken(token: String) {
        val map = HashMap<String, String>()
        map["token"] = token
        Firebase.firestore.collection(Const.META_DATA).document(AppController.sharedPref.getUsername())
            .set(map)
    }

}