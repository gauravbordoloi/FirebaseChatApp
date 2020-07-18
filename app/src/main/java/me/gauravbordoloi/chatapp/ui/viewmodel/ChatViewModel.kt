package me.gauravbordoloi.chatapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.gauravbordoloi.chatapp.data.Repository
import me.gauravbordoloi.chatapp.data.model.Chat

class ChatViewModel : ViewModel() {

    private val repository = Repository.getInstance()

    fun getChats(): MutableLiveData<List<Chat>?> {
        return repository.getChatsData()
    }

    fun observeChat(): MutableLiveData<Chat?> {
        return repository.getChatData()
    }

    fun sendChat(text: String) {
        repository.sendChat(text)
    }

    fun clear() {
        repository.clear()
    }

}