package me.gauravbordoloi.chatapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.data.model.Chat
import me.gauravbordoloi.chatapp.util.Const

class Repository {

    private val TAG = javaClass.simpleName

    private val chat = MutableLiveData<Chat?>()
    private val chats = MutableLiveData<List<Chat>?>()

    private val db = Firebase.firestore.collection(Const.CONVERSATION)

    companion object {

        private var instance: Repository? = null

        fun getInstance(): Repository {
            if (instance == null) {
                instance = Repository()
            }
            return instance!!
        }

    }

    init {
        getChats()
    }

    private fun getChats() {
        GlobalScope.launch(Dispatchers.IO) {
            db.orderBy("time", Query.Direction.ASCENDING).get()
                .addOnSuccessListener {
                    chats.postValue(it.toObjects(Chat::class.java))
                    getChat()
                }.addOnFailureListener {
                    Log.e(TAG, "getChats", it)
                    chats.postValue(null)
                    getChat()
                }
        }
    }

    private fun getChat() {
        GlobalScope.launch(Dispatchers.IO) {
            db.addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(TAG, "getChat", error)
                    return@addSnapshotListener
                }
                if (value != null && !value.isEmpty) {
                    for (dc in value.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            val c = dc.document.toObject(Chat::class.java)
                            Log.e(TAG, c.toString())
                            chat.postValue(c)
                        }
                    }
                }
            }
        }
    }

    fun getChatsData() = chats

    fun getChatData() = chat

    fun sendChat(text: String) {
        val map = HashMap<String, Any>()
        map["time"] = System.currentTimeMillis()
        map["from"] = AppController.sharedPref.getUsername()
        map["to"] = Const.getSender()
        map["message"] = text
        db.add(map).addOnCompleteListener {
            if (it.exception != null) {
                Log.e(TAG, "sendChat", it.exception)
            }
        }
    }

    fun clear() {
        instance = null
    }

}