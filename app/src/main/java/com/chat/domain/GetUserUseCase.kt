package com.chat.domain

import com.chat.data.ConversationRepository
import com.chat.data.db.myUserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository,
) {
     operator fun invoke(conversationId: String): Flow<String> =
        conversationRepository.getConversationWithUsers(conversationId)
            .mapNotNull {
                it?.users
                    ?.firstOrNull { it.userId != myUserId }
                    ?.name
            }
}