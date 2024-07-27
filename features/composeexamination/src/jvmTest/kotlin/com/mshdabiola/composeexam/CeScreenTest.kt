package com.mshdabiola.composeexam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import kotlin.test.Test

class CeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            ComposeExaminationScreen(
                modifier = Modifier.fillMaxSize(),
                ceState = CeState.Success(isUpdate = false, subjects = emptyList()),
                subject = rememberTextFieldState("Math"),
                duration = rememberTextFieldState("12"),
                year = rememberTextFieldState("1556"),
                addExam = {},
                )


        }
        composeRule.onNodeWithTag("ce:screen").assertExists()
        composeRule.onNodeWithTag("ce:subject").assertExists()
        composeRule.onNodeWithTag("ce:year").assertExists()
        composeRule.onNodeWithTag("ce:duration").assertExists()
    }
}
