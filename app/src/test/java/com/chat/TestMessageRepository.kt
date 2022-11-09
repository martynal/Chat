package com.chat

import com.chat.data.MessageRepository
import com.chat.data.db.model.Message
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestMessageRepository : MessageRepository {

    /**
     * The backing hot flow for the list of messages for testing.
     */
    private val messagesFlow: MutableSharedFlow<List<Message>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getMessagesByConversationId(conversationId: String): Flow<List<Message>> =
        messagesFlow

    override suspend fun insertMessage(message: Message) {
    }

    /**
     * A test-only API to allow controlling the list of messages from tests.
     */
    fun sendMessages(messages: List<Message>) {
        messagesFlow.tryEmit(messages)
    }
}