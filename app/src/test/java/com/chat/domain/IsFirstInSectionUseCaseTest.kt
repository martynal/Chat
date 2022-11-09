package com.chat.domain

import com.chat.data.db.model.Message
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class IsFirstInSectionUseCaseTest {

    val useCase = IsFirstInSectionUseCase()

    @Test
    fun prevMessageIsSentMoreThanHourAgo_shouldReturnTrue() {
        val instant = Instant.now()
        val message = Message("0", "Hi", "0", "0", instant)
        val prevMessage = Message("0", "Hi", "0", "0", instant.minus(2, ChronoUnit.HOURS))
        val isFirstInSection = useCase(message, prevMessage)
        Assert.assertEquals(true, isFirstInSection)
    }

    @Test
    fun prevMessageIsSentExactlyHourAgo_shouldReturnFalse() {
        val instant = Instant.now()
        val message = Message("0", "Hi", "0", "0", instant)
        val prevMessage = Message("0", "Hi", "0", "0", instant.minus(1, ChronoUnit.HOURS))
        val isFirstInSection = useCase(message, prevMessage)
        Assert.assertEquals(false, isFirstInSection)
    }

    @Test
    fun prevMessageIsSentLessThenHourAgo_shouldReturnFalse() {
        val instant = Instant.now()
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val prevMessage = Message("0", "Hi", "0", "0", instant.minus(59, ChronoUnit.MINUTES))
        val isFirstInSection = useCase(message, prevMessage)
        Assert.assertEquals(false, isFirstInSection)
    }

    @Test
    fun thereIsNoPrevMessage_shouldReturnTrue() {
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val isFirstInSection = useCase(message, null)
        Assert.assertEquals(true, isFirstInSection)
    }
}