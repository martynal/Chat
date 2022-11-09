package com.chat.data

import com.chat.data.db.model.ConversationWithUsers
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
     fun getConversationWithUsers(conversationId: String): Flow<ConversationWithUsers?>
}