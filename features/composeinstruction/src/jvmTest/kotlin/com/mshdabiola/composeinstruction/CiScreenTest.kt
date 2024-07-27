package com.mshdabiola.composeinstruction

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import kotlin.test.Test

class CiScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            CiScreen(
                modifier = Modifier.fillMaxSize(),
                ciState = CiState.Success(
                    content = listOf(ItemUiState()).toImmutableList(),
                    id = 0,
                    examId = 0,
                ),
                instructionInput = rememberTextFieldState(),
                title = rememberTextFieldState(),

            )
        }
        composeRule.onNodeWithTag("ci:screen").assertExists()
        composeRule.onNodeWithTag("ci:title").assertExists()
        composeRule.onNodeWithTag("ci:content").assertExists()
        composeRule.onNodeWithTag("ci:add_instruction").assertExists()
    }
}
