package com.chat.domain

import com.chat.data.db.model.Message
import java.time.Duration
import javax.inject.Inject


class HasTailUseCase @Inject constructor() {
    private val hasTailPeriod = Duration.ofSeconds(20)

    operator fun invoke(message: Message, nextMessage: Message?): Boolean {
        val nextAuthor = nextMessage?.userId
        val isLastMessageByAuthor = nextAuthor != message.userId

        val isMoreThanTwentySecondsBetween =
            nextMessage?.let { nMessage ->
                val twentySecondsAfter = nMessage.createdAt.minus(hasTailPeriod)
                message.createdAt.isBefore(twentySecondsAfter)
            } ?: false

        return isLastMessageByAuthor || isMoreThanTwentySecondsBetween
    }
}
