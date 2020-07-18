package me.gauravbordoloi.chatapp.data.model

import androidx.annotation.Keep

@Keep
data class Chat(
    val time: Long? = 0,
    val from: String? = "",
    val to: String? = "",
    val message: String? = ""
)