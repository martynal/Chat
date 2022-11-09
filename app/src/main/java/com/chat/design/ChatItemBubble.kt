package com.chat.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.chat.design.size.ChatSizing.ChatBubbleShape
import com.chat.design.size.ChatSizing.ChatBubbleTailShape
import com.chat.design.size.ChatSizing.UserChatBubbleTailShape
import com.chat.domain.model.ChatMessage

@Composable
fun ChatItemBubble(
    message: ChatMessage,
) {
    val backgroundBubbleColor = if (message.isUserMe) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.secondaryVariant
    }

    Column(
        horizontalAlignment = if (message.isUserMe) Alignment.End else Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    )
    {
        if (message.isFirstInSection) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(
                            message.day
                        )
                    }
                    append(" ")
                    append(message.time)
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Surface(
            color = backgroundBubbleColor,
            shape =
            if (message.hasTail)
                if (message.isUserMe)
                    UserChatBubbleTailShape
                else
                    ChatBubbleTailShape
            else ChatBubbleShape,
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}