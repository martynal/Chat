package com.chat.domain

import com.chat.data.db.model.Message
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class IsFirstInSectionUseCase @Inject constructor() {
    private val firstInSectionPeriod = Duration.ofHours(1)

    operator fun invoke(message: Message, prevMessage: Message?): Boolean {
        return prevMessage?.createdAt?.let { pMessage ->
            val hourAgo = message.createdAt.minus(firstInSectionPeriod)
            pMessage.isBefore(hourAgo)
        } ?: true
    }
}
