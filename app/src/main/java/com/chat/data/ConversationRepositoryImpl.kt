package com.chat.data

import com.chat.data.db.dao.ConversationDao
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val conversationDao: ConversationDao,
) : ConversationRepository {
    override  fun getConversationWithUsers(conversationId: String) =
        conversationDao.getConversationWithUsers(conversationId)

}