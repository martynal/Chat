package com.chat.design

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.chat.R
import com.chat.ui.theme.ChatTheme

@Composable
fun ChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        ),
        maxLines = 4,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.value_hint),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        shape = RoundedCornerShape(50),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun ChatTextFieldPreview() {
    ChatTheme {
        Surface {
            Column {
                ChatTextField(value = "", onValueChange = {})
                ChatTextField(value = "Hi! Let's talk :)", onValueChange = {})
            }
        }
    }
}