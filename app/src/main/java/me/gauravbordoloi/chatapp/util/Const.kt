package me.gauravbordoloi.chatapp.util

import me.gauravbordoloi.chatapp.AppController

object Const {

    const val CONVERSATION = "conversation"
    const val META_DATA = "meta_data"

    const val USERNAME_1 = "github"
    const val USERNAME_2 = "gitlab"

    const val CHANNEL_ID = "Chat App"

    fun getSender() = if (AppController.sharedPref.getUsername() == USERNAME_1) {
        USERNAME_2
    } else {
        USERNAME_1
    }

}