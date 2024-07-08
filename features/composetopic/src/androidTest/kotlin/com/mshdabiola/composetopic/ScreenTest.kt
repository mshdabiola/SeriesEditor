/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [CtScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
//            CtScreen(
//                mainState = Result.Loading,
//            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
