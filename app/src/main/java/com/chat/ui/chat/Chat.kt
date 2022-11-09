package com.chat.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chat.R
import com.chat.design.ChatItemBubble
import com.chat.design.icon.ChatIcons
import com.chat.design.size.ChatSizing
import kotlinx.coroutines.launch

@Composable
fun Chat(chatViewModel: ChatViewModel = hiltViewModel()) {

    val state by chatViewModel.state.collectAsState()

    ChatContent(state = state)
}

@Composable
fun ChatContent(
    state: ChatState,
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state is ChatState.Result) state.userName else stringResource(id = R.string.app_name),
                        modifier = Modifier
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(id = ChatIcons.ArrowBack),
                            null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            when (state) {
                ChatState.Loading -> LinearProgressIndicator()
                is ChatState.Result -> {
                    Messages(
                        scrollState = scrollState,
                        chatState = state,
                        modifier = Modifier.weight(1f),
                    )
                    UserInput(
                        resetScroll = {
                            scope.launch {
                                scrollState.scrollToItem(0)
                            }
                        }
                    )
                }
                ChatState.Error -> Unit
            }
        }
    }
}

@Composable
fun Messages(
    scrollState: LazyListState,
    chatState: ChatState.Result,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = scrollState,
        reverseLayout = true,
        contentPadding = PaddingValues(
            horizontal = ChatSizing.DefaultPadding.dp,
            vertical = ChatSizing.HalfPadding.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(chatState.message) { message ->
            ChatItemBubble(message = message)
        }
    }
}



