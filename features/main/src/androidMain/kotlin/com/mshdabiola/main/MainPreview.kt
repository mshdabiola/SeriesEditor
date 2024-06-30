package com.mshdabiola.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.state.SubjectUiState

@Preview
@Composable
private fun MainPreview() {
    MainScreen(
        examUiState = null,

        screenSize = ScreenSize.COMPACT,
        subjectUiState = SubjectUiState(name = "Math"),
    )
}