package com.mshdabiola.subjects

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.subjectWithSeries
import com.mshdabiola.testing.subjects
import org.junit.Rule
import kotlin.test.Test

class SubjectScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            SubjectScreen(
                modifier = Modifier.fillMaxSize(),
                mainState = Result.Success(
                    subjectWithSeries,
                ),
            )
        }

        composeRule.onNodeWithTag("subjects:screen").assertExists()
        composeRule.onNodeWithTag("subjects:list").assertExists()
        composeRule.onNodeWithTag("subjects:loading").assertDoesNotExist()
        composeRule.onNodeWithTag("subjects:empty").assertDoesNotExist()
        composeRule.onNodeWithTag("subjects:list").onChildren().assertCountEquals(9)
    }
}
