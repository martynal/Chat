package com.chat.domain

import com.chat.data.MessageRepositoryImpl
import com.chat.data.db.model.Message
import com.chat.data.db.myUserId
import com.chat.data.db.userSarahId
import com.chat.domain.model.ChatMessage
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit


class GetChatMessagesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val messageRepository = mockk<MessageRepositoryImpl>()
    private val formatDateUseCase = mockk<FormatDateUseCase>()
    private val isFirstInSectionUseCase = mockk<IsFirstInSectionUseCase>()
    private val hasTailUseCase = mockk<HasTailUseCase>()
    private val instant = Instant.now()

    val useCase = GetChatMessagesUseCase(
        messageRepository,
        formatDateUseCase,
        isFirstInSectionUseCase,
        hasTailUseCase
    )


    @Before
    fun before() {

        coEvery { messageRepository.getMessagesByConversationId("0") } returns flow {
            emit(
                listOf(
                    Message("0", "Hi", myUserId, "0", instant),
                    Message("1", "Hi 1", myUserId, "0", instant.plusSeconds(5)),
                    Message("2", "Hi 2", myUserId, "0", instant.plusSeconds(6)),
                    Message("3", "Hi 3", userSarahId, "0", instant.plusSeconds(7)),
                    Message("4", "Hi 4", userSarahId, "0", instant.plusSeconds(28)),
                    Message("5", "Hi 5", userSarahId, "0", instant.plusSeconds(29)),
                    Message("6", "Hi 6", userSarahId, "0", instant.plus(2, ChronoUnit.HOURS)),
                )
            )
        }
        every { hasTailUseCase(any(), any()) } returns true
        every { isFirstInSectionUseCase(any(), any()) } returns true
        every { formatDateUseCase(any()) } returns listOf("Wednesday", "11:13")
    }

    @Test
    fun getChatMessagesUseCase() = runTest {
        val chatMessages = useCase("0")
        assertEquals(
            listOf(
                ChatMessage(
                    "Hi",
                    isUserMe = true,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 1",
                    isUserMe = true,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 2",
                    isUserMe = true,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 3",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 4",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 5",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
                ChatMessage(
                    "Hi 6",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Wednesday",
                    time = "11:13"
                ),
            ), chatMessages.first()
        )
    }
}