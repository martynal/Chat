package com.chat.data

import com.chat.data.db.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
     fun getMessagesByConversationId(conversationId: String): Flow<List<Message>>
    suspend fun insertMessage(message: Message)
}