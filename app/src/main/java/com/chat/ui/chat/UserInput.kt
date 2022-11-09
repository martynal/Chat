package com.chat.ui.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chat.R
import com.chat.design.ChatButton
import com.chat.design.ChatTextField
import com.chat.design.icon.ChatIcons
import com.chat.design.size.ChatSizing
import com.chat.ui.theme.ChatTheme


@Composable
fun UserInput(
    userInputViewModel: UserInputViewModel = hiltViewModel(),
    resetScroll: () -> Unit,
) {
    UserInputContent(
        textFieldValue = userInputViewModel.textFieldValue,
        updateTextFieldValue = userInputViewModel::updateTextFieldValue,
        sendMessage = userInputViewModel::sendMessage,
        reply = userInputViewModel::reply,
        resetScroll = resetScroll
    )
}

@Composable
fun UserInputContent(
    textFieldValue: String,
    updateTextFieldValue: (String) -> Unit,
    sendMessage: (String) -> Unit,
    reply: () -> Unit,
    resetScroll: () -> Unit,
) {
    Surface(elevation = 16.dp) {
        Column(modifier = Modifier.padding(ChatSizing.HalfPadding.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                ChatTextField(
                    value = textFieldValue,
                    onValueChange = updateTextFieldValue,
                    modifier = Modifier
                        .weight(1f, false)
                        .testTag(ChatTextFieldTestTag)
                )
                ChatButton(
                    icon = ChatIcons.Send,
                    onClick = {
                        sendMessage(textFieldValue)
                        resetScroll()
                    }
                )
            }
            Button(
                onClick = reply,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(id = R.string.reply))
            }
        }
    }
}

const val ChatTextFieldTestTag = "ChatTextFieldTestTag"

@Preview
@Composable
fun UserInputPreview() {
    ChatTheme {
        Surface {
            UserInputContent("Hi!", {}, {}, {}, {})
        }
    }
}