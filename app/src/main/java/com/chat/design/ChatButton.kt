package com.chat.design

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chat.design.icon.ChatIcons
import com.chat.design.size.ChatSizing
import com.chat.ui.theme.ChatTheme

@Composable
fun ChatButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    size: Dp = ChatSizing.DefaultIconSize.dp,
) {

    Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        tint = MaterialTheme.colors.primary,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .size(size)
            .padding(8.dp)
    )
}

@Preview
@Composable
fun ChatButtonPreview() {
    ChatTheme {
        Surface {
            ChatButton(ChatIcons.Send,{})
        }
    }
}