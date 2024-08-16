package com.mshdabiola.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import com.mshdabiola.seriesmodel.Series
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
                mainState = MainState.Success(
                    series = listOf(Series(1, 6, "ask")),
                ),
                subjectState = rememberTextFieldState("subject"),
            )
        }
    }
}
