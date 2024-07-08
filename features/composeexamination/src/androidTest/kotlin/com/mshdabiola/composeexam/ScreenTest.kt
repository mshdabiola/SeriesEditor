/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [ComposeExaminationScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
//            ComposeExaminationScreen(
//                mainState = Result.Loading,
//            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
