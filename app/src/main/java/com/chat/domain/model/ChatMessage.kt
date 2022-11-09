package com.chat.domain.model

data class ChatMessage(
    val text: String,
    val isUserMe: Boolean,
    val hasTail: Boolean,
    val isFirstInSection: Boolean,
    val day: String,
    val time: String,
)