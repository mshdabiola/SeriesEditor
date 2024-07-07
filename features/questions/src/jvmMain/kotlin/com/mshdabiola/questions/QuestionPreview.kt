package com.mshdabiola.questions

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.mshdabiola.data.model.Result

@Preview
@Composable
private fun QuestionPreview() {
    QuestionsScreen(
        mainState = Result.Success(listOf()),
    )
}