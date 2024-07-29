package com.mshdabiola.questions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.questions
import com.mshdabiola.ui.toQuestionUiState
import org.junit.Rule
import kotlin.test.Test

class QuestionScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            QuestionsScreen (
                modifier = Modifier.fillMaxSize(),
                mainState = Result.Success(
                    questions.map { it.toQuestionUiState() }
                )
            )
        }

        composeRule.onNodeWithTag("question:screen").assertExists()
        composeRule.onNodeWithTag("question:list").assertExists()

    }
}
