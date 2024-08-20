package com.mshdabiola.instructions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.instructions
import com.mshdabiola.ui.toInstructionUiState
import org.junit.Rule
import kotlin.test.Test

class InstructionScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            InstructionScreen(
                modifier = Modifier.fillMaxSize(),
                mainState = Result.Success(
                    instructions.map { it.toInstructionUiState() },
                ),
            )
        }

        composeRule.onNodeWithTag("instruction:screen").assertExists()
        composeRule.onNodeWithTag("instruction:list").assertExists()
    }
}
