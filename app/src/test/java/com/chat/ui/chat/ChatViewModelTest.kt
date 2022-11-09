package com.chat.ui.chat

import com.chat.TestConversationRepository
import com.chat.TestMessageRepository
import com.chat.data.db.model.Conversation
import com.chat.data.db.model.ConversationWithUsers
import com.chat.data.db.model.Message
import com.chat.data.db.model.User
import com.chat.data.db.myUserId
import com.chat.data.db.userSarahId
import com.chat.domain.*
import com.chat.domain.model.ChatMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
internal class ChatViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val messageRepository = TestMessageRepository()
    private val conversationRepository = TestConversationRepository()

    private lateinit var viewModel: ChatViewModel

    @Before
    fun before() {
        viewModel = ChatViewModel(
            GetChatMessagesUseCase(
                messageRepository,
                FormatDateUseCase(
                    zoneId = ZoneId.of("Europe/London"),
                    locale = Locale.ENGLISH
                ),
                IsFirstInSectionUseCase(),
                HasTailUseCase()
            ),
            GetUserUseCase(conversationRepository)
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(ChatState.Loading, viewModel.state.value)
    }

    @Test
    fun stateIsCHatStateResult() = runTest {
        val collectJob1 = launch(UnconfinedTestDispatcher()) { viewModel.state.collect() }
        conversationRepository.sendConversationWithUsers(
            ConversationWithUsers(
                Conversation("0"),
                listOf(User(userSarahId, "Sarah"), User(myUserId, "David"))
            )
        )
        val instant = Instant.parse("2018-10-20T16:55:30.00Z")
        messageRepository.sendMessages(
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

        assertEquals(
            ChatState.Result(
                listOf(
                    ChatMessage(
                        "Hi 6",
                        isUserMe = false,
                        hasTail = true,
                        isFirstInSection = true,
                        day="Saturday",
                        time="19:55"
                    ),
                    ChatMessage(
                        "Hi 5",
                        isUserMe = false,
                        hasTail = true,
                        isFirstInSection = false,
                        day="Saturday",
                        time="17:55"
                    ),
                    ChatMessage(
                        "Hi 4",
                        isUserMe = false,
                        hasTail = false,
                        isFirstInSection = false,
                        day="Saturday",
                        time="17:55"
                    ),
                    ChatMessage(
                        "Hi 3",
                        isUserMe = false,
                        hasTail = true,
                        isFirstInSection = false,
                        day="Saturday",
                        time="17:55"
                    ),
                    ChatMessage(
                        "Hi 2",
                        isUserMe = true,
                        hasTail = true,
                        isFirstInSection = false,
                        day="Saturday",
                        time="17:55"
                    ),
                    ChatMessage(
                        "Hi 1",
                        isUserMe = true,
                        hasTail = false,
                        isFirstInSection = false,
                        day="Saturday",
                        time="17:55"
                    ),
                    ChatMessage(
                        "Hi",
                        isUserMe = true,
                        hasTail = false,
                        isFirstInSection = true,
                        day="Saturday",
                        time="17:55"
                    )
                ),
                userName = "Sarah"
            ), viewModel.state.first()
        )
        collectJob1.cancel()
    }

}