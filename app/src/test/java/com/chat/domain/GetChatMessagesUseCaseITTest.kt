package com.chat.domain

import com.chat.data.MessageRepositoryImpl
import com.chat.data.db.dao.MessageDao
import com.chat.data.db.model.Message
import com.chat.data.db.myUserId
import com.chat.data.db.userSarahId
import com.chat.domain.model.ChatMessage
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetChatMessagesUseCaseITTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val messageDao = mockk<MessageDao>()

    private val messageRepository = MessageRepositoryImpl(messageDao)
    private val formatDateUseCase = FormatDateUseCase(ZoneId.of("Europe/London"), Locale.ENGLISH)
    private val isFirstInSectionUseCase = IsFirstInSectionUseCase()
    private val hasTailUseCase = HasTailUseCase()
    private val instant = Instant.parse("2018-10-20T16:55:30.00Z")


    val useCase = GetChatMessagesUseCase(
        messageRepository,
        formatDateUseCase,
        isFirstInSectionUseCase,
        hasTailUseCase
    )

    @Before
    fun before() {

        coEvery { messageDao.getAllByConversation("0") } returns flow {
            emit(
                listOf(
                    Message("6", "Hi 6", userSarahId, "0", instant.plus(2, ChronoUnit.HOURS)),
                    Message("5", "Hi 5", userSarahId, "0", instant.plusSeconds(29)),
                    Message("4", "Hi 4", userSarahId, "0", instant.plusSeconds(28)),
                    Message("3", "Hi 3", userSarahId, "0", instant.plusSeconds(7)),
                    Message("2", "Hi 2", myUserId, "0", instant.plusSeconds(6)),
                    Message("1", "Hi 1", myUserId, "0", instant.plusSeconds(5)),
                    Message("0", "Hi", myUserId, "0", instant),
                )
            )
        }
    }

    @Test
    fun getChatMessagesUseCase() = runTest {
        val chatMessages = useCase("0")
        assertEquals(
            listOf(
                ChatMessage(
                    "Hi 6",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = true,
                    day = "Saturday",
                    time = "19:55"
                ),
                ChatMessage(
                    "Hi 5",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = false,
                    day = "Saturday",
                    time = "17:55"
                ),
                ChatMessage(
                    "Hi 4",
                    isUserMe = false,
                    hasTail = false,
                    isFirstInSection = false,
                    day = "Saturday",
                    time = "17:55"
                ),
                ChatMessage(
                    "Hi 3",
                    isUserMe = false,
                    hasTail = true,
                    isFirstInSection = false,
                    day = "Saturday",
                    time = "17:55"
                ),
                ChatMessage(
                    "Hi 2",
                    isUserMe = true,
                    hasTail = true,
                    isFirstInSection = false,
                    day = "Saturday",
                    time = "17:55"
                ),
                ChatMessage(
                    "Hi 1",
                    isUserMe = true,
                    hasTail = false,
                    isFirstInSection = false,
                    day = "Saturday",
                    time = "17:55"
                ),
                ChatMessage(
                    "Hi",
                    isUserMe = true,
                    hasTail = false,
                    isFirstInSection = true,
                    day = "Saturday",
                    time = "17:55"
                ),
            ), chatMessages.first()
        )
    }
}