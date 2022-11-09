package com.chat.data

import com.chat.data.db.dao.MessageDao
import com.chat.data.db.model.Message
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
) : MessageRepository {
    override fun getMessagesByConversationId(conversationId: String) =
        messageDao.getAllByConversation(conversationId)

    override suspend fun insertMessage(message: Message) {
        messageDao.insert(message)
    }
}