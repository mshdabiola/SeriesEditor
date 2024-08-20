package com.mshdabiola.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import kotlin.test.Test

class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                user = TextFieldState(""),
                password = TextFieldState(""),

            )
        }
    }
}
