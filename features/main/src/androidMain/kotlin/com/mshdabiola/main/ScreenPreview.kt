package com.mshdabiola.main

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.DevicePreviews

@DevicePreviews
@Composable
private fun ComposeSubjectPreview() {
    MainScreen(
        subjectState = rememberTextFieldState("subject"),
        mainState = MainState(
            series = listOf(com.mshdabiola.seriesmodel.Series(1, 6, "ask")),
        ),
    )
}
