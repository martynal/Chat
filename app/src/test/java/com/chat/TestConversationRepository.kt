package com.chat

import com.chat.data.ConversationRepository
import com.chat.data.db.model.ConversationWithUsers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestConversationRepository : ConversationRepository {

    /**
     * The backing hot flow for the list of conversation for testing.
     */
    private val conversationWithUsersFlow: MutableSharedFlow<ConversationWithUsers> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    /**
     * A test-only API to allow controlling the list of conversation with users from tests.
     */
    fun sendConversationWithUsers(conversationWithUsers: ConversationWithUsers) {
        conversationWithUsersFlow.tryEmit(conversationWithUsers)
    }

    override fun getConversationWithUsers(conversationId: String): Flow<ConversationWithUsers?> =
        conversationWithUsersFlow
}