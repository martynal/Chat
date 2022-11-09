package com.chat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chat.data.db.conversationId
import com.chat.domain.GetChatMessagesUseCase
import com.chat.domain.GetUserUseCase
import com.chat.domain.model.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class ChatState {
    object Loading : ChatState()
    data class Result(val message: List<ChatMessage>, val userName: String) : ChatState()
    object Error : ChatState()
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    getChatMessagesUseCase: GetChatMessagesUseCase,
    getUserUseCase: GetUserUseCase,
) : ViewModel() {

    val state: StateFlow<ChatState> =
        combine(
            getChatMessagesUseCase(conversationId),
            getUserUseCase(conversationId)
        ) { messages, userName ->
            ChatState.Result(messages, userName)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChatState.Loading
        )
}