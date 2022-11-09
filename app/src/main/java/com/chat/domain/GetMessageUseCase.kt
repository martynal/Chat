package com.chat.domain

import com.chat.data.db.model.Message
import java.time.Instant
import java.util.*
import javax.inject.Inject

class GetMessageUseCase @Inject constructor() {
    operator fun invoke(message: String, conversationId: String, userId: String) = Message(
        UUID.randomUUID().toString(),
        message,
        userId,
        conversationId,
        Instant.now()
    )
}