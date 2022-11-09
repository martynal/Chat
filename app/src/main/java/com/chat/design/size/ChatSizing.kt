package com.chat.design.size

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object ChatSizing {
    const val DefaultIconSize = 48
    const val DefaultPadding = 24
    const val HalfPadding = 12

    val UserChatBubbleTailShape = RoundedCornerShape(14.dp, 14.dp, 0.dp, 14.dp)
    val ChatBubbleTailShape = RoundedCornerShape(14.dp, 14.dp, 14.dp, 0.dp)
    val ChatBubbleShape = RoundedCornerShape(14.dp, 14.dp, 14.dp, 14.dp)
}