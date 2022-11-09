package com.chat.domain

import com.chat.data.db.model.Message
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class HasTailUseCaseTest {

    val useCase = HasTailUseCase()

    @Test
    fun messageIsMostRecentInConversation_shouldReturnTrue() {
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val hasTail = useCase(message, null)
        assertEquals(true, hasTail)
    }

    @Test
    fun messageAfterIsSentNineteenSeconds_shouldReturnFalse() {
        val instant = Instant.now()
        val message = Message("0", "Hi", "0", "0", instant)
        val nextMessage = Message("1", "How are you?", "0", "0", instant.plusSeconds(19))
        val hasTail = useCase(message, nextMessage)
        assertEquals(false, hasTail)
    }

    @Test
    fun messageAfterIsSentExactlyTwentySeconds_shouldReturnFalse() {
        val instant = Instant.now()
        val message = Message("0", "Hi", "0", "0", instant)
        val nextMessage = Message("1", "How are you?", "0", "0", instant.plusSeconds(20))
        val hasTail = useCase(message, nextMessage)
        assertEquals(false, hasTail)
    }

    @Test
    fun messageAfterIsSentAfterTwentySeconds_shouldReturnTrue() {
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val nextMessage = Message("1", "How are you?", "0", "0", Instant.now().plusSeconds(21))
        val hasTail = useCase(message, nextMessage)
        assertEquals(true, hasTail)
    }

    @Test
    fun messageAfterIsSentByOtherUser_shouldReturnTrue() {
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val nextMessage = Message("1", "How are you?", "1", "0", Instant.now())
        val hasTail = useCase(message, nextMessage)
        assertEquals(true, hasTail)
    }

    @Test
    fun messageAfterIsSentBeforeTwentySeconds_shouldReturnFalse() {
        val message = Message("0", "Hi", "0", "0", Instant.now())
        val nextMessage = Message("1", "How are you?", "0", "0", Instant.now())
        val hasTail = useCase(message, nextMessage)
        assertEquals(false, hasTail)
    }
}