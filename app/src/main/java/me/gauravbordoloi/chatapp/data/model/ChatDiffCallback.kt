package me.gauravbordoloi.chatapp.data.model

import androidx.recyclerview.widget.DiffUtil

class ChatDiffCallback (val old: List<Chat>, val new: List<Chat>): DiffUtil.Callback() {

    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].time == new[newItemPosition].time
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (time, from, to, message) = old[oldItemPosition]
        val (time1, from1, to1, message1) = new[newItemPosition]

        return time == time1 && from == from1 && to == to1 && message == message1
    }

}