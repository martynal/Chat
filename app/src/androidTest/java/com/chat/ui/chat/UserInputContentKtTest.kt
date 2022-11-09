package com.chat.ui.chat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.chat.ui.theme.ChatTheme
import org.junit.Rule
import org.junit.Test

class UserInputContentKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun balancesStateResult() {
        composeTestRule.setContent {
            ChatTheme {
                UserInputContent(
                    "",
                    {},
                    {},
                    {},
                    {}
                )
            }
        }
        composeTestRule.onNodeWithTag(ChatTextFieldTestTag).assertIsDisplayed()
    }
}