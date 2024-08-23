package com.mshdabiola.main

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
private fun ExportDialogPreview() {
    ExportDialog(
        show = true,
        passwordState = rememberTextFieldState("password"),
        exams = ExportState.Success(
            listOf(
                ExamState(1, "subject", 2022, "class", true),
                ExamState(2, "subject", 2022, "class", false),
                ExamState(3, "subject", 2022, "class", false),
                ExamState(4, "subject", 2022, "class", true),
                ExamState(5, "subject", 2022, "class", false),
                ExamState(6, "subject", 2022, "class", true),
                ExamState(7, "subject", 2022, "class", false),
                ExamState(8, "subject", 2022, "class", false),
            ),
        ),
        onExport = {},
        onExamSelected = {},
        onDismiss = {},
    )

}