package me.gauravbordoloi.chatapp.ui.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat_me.view.*
import me.gauravbordoloi.chatapp.data.model.Chat
import me.gauravbordoloi.chatapp.util.Helper

class MyChatViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val textViewMessage = view.textViewMessage
    private val textViewTime = view.textViewTime

    fun bind(chat: Chat) {
        textViewMessage.text = chat.message
        textViewTime.text = Helper.getTime(chat.time)
    }

}