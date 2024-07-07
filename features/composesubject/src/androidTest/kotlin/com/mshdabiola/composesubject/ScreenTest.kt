/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.data.model.Result
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [SubjectScreen] composable.
 */
class ScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
            SubjectScreen(
                mainState = Result.Loading,
            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
