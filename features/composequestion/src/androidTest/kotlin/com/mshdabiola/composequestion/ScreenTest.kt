/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [CqScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
//            CqScreen(
//                mainState = Result.Loading,
//            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
