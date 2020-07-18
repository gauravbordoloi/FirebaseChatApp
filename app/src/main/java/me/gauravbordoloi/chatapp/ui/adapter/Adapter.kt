package me.gauravbordoloi.chatapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.gauravbordoloi.chatapp.AppController
import me.gauravbordoloi.chatapp.R
import me.gauravbordoloi.chatapp.data.model.Chat
import me.gauravbordoloi.chatapp.data.model.ChatDiffCallback
import me.gauravbordoloi.chatapp.ui.view.viewholder.MyChatViewHolder
import me.gauravbordoloi.chatapp.ui.view.viewholder.OtherChatViewHolder

class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chats = arrayListOf<Chat>()

    fun setChats(list: List<Chat>) {
        val diffCallback = ChatDiffCallback(chats, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        chats.clear()
        chats.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addChat(chat: Chat) {
        /*Using DiffUtil here is not efficient as DiffUtil uses list to compare and it uses
        "notifyItemRangeInserted" under the hood whereas we need to use "notifyItemInserted"*/
        chats.add(chat)
        notifyItemInserted(itemCount - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (chats[position].from == AppController.sharedPref.getUsername()) {
            R.layout.item_chat_me
        } else {
            R.layout.item_chat_other
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_chat_me) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_me, parent, false)
            MyChatViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
            OtherChatViewHolder(view)
        }
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chats[position]
        if (getItemViewType(position) == R.layout.item_chat_me) {
            (holder as MyChatViewHolder).bind(chat)
        } else {
            (holder as OtherChatViewHolder).bind(chat)
        }
    }

}