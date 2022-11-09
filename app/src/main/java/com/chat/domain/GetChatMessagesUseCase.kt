package com.chat.domain

import com.chat.data.MessageRepository
import com.chat.data.db.myUserId
import com.chat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val formatDateUseCase: FormatDateUseCase,
    private val isFirstInSectionUseCase: IsFirstInSectionUseCase,
    private val hasTailUseCase: HasTailUseCase,
) {
    operator fun invoke(conversationId: String): Flow<List<ChatMessage>> =
        messageRepository.getMessagesByConversationId(conversationId)
            .map { messages ->
                messages.mapIndexed { index, message ->

                    val hasTail = hasTailUseCase(message, messages.getOrNull(index - 1))

                    val isFirstInSection =
                        isFirstInSectionUseCase(message, messages.getOrNull(index + 1))

                    val isUserMe = message.userId == myUserId

                    val timestamp = formatDateUseCase(message.createdAt)

                    ChatMessage(
                        text = message.text,
                        isUserMe = isUserMe,
                        hasTail = hasTail,
                        isFirstInSection = isFirstInSection,
                        day = timestamp[0],
                        time = timestamp[1]
                    )
                }
            }
}