package com.mshdabiola.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews

@DevicePreviews
@Composable
private fun ComposeSubjectPreview() {
    LoginScreen(
        modifier = Modifier.fillMaxSize(),
        user = TextFieldState(""),
        password = TextFieldState(""),

    )
}
