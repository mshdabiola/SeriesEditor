/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [CiScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
//            CiScreen(
//                mainState = Result.Loading,
//            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
