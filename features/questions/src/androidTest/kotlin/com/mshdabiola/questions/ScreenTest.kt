/*
 *abiola 2022
 */

package com.mshdabiola.questions

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.data.model.Result
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [QuestionsScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
            QuestionsScreen(
                mainState = Result.Loading,
            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
