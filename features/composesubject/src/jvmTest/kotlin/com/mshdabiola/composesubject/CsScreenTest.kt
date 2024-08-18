package com.mshdabiola.composesubject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import kotlin.test.Test

class CsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            SubjectScreen(
                modifier = Modifier.fillMaxSize(),
                csState = CsState.Success(
                    id = 1,
                ),
                subjectState = rememberTextFieldState("subject"),
            )
        }
        composeRule.onNodeWithTag("cs:screen").assertExists()
        composeRule.onNodeWithTag("cs:subject").assertExists()
        composeRule.onNodeWithTag("cs:add_subject").assertExists()
        composeRule.onNodeWithTag("cs:delete_series").assertDoesNotExist()
    }
}
