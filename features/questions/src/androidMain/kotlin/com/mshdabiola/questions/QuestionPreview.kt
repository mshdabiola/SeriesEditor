package com.mshdabiola.questions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.data.model.Result

@Preview
@Composable
private fun QuestionPreview() {
    QuestionsScreen(
        mainState = Result.Loading
    )
}