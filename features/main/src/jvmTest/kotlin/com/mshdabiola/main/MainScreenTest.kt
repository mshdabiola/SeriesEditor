package com.mshdabiola.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.examinations
import com.mshdabiola.ui.toUi
import org.junit.Rule
import kotlin.test.Test

class MainScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                mainState = Result.Success(
                    examinations.map { it.toUi() },
                ),
            )
        }

        composeRule.onNodeWithTag("main:screen").assertExists()
        composeRule.onNodeWithTag("main:list").assertExists()
    }
}
