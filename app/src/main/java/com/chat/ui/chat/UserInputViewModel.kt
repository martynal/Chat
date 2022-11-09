package com.chat.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chat.data.MessageRepository
import com.chat.data.db.conversationId
import com.chat.data.db.myUserId
import com.chat.data.db.userSarahId
import com.chat.domain.GetMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val getMessageUseCase: GetMessageUseCase,
) : ViewModel() {

    var textFieldValue by mutableStateOf("")
        private set

    fun updateTextFieldValue(value: String) {
        textFieldValue = value
    }

    fun sendMessage(text: String, userId: String = myUserId) {
        viewModelScope.launch(Dispatchers.IO) {
            val message = getMessageUseCase(text, conversationId, userId)
            messageRepository.insertMessage(message)
            updateTextFieldValue("")
        }
    }

    fun reply() {
        sendMessage(LoremIpsum().values.first().take((0..300).random()), userSarahId)
    }
}