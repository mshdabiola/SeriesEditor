/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import com.mshdabiola.main.MainScreen
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [MainScreen] composable.
 */
class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
//
            MainScreen(
                examUiState = null,
                subjects = listOf(SubjectUiState(1, name = "Math"),SubjectUiState(2,"English")).toImmutableList(),
                screenSize = ScreenSize.COMPACT,
                subjectUiState = SubjectUiState(id = 1, name = "Math"),
            )
        }

        composeTestRule
            .onNodeWithTag("add")
            .assertExists()

        composeTestRule
            .onNodeWithTag("add")
            .performClick()

      val node=  composeTestRule
            .onNodeWithTag("main:subject:dropdown")

        node.assertExists()
        node.performClick()

      val item=  composeTestRule
            .onNodeWithTag("dropdown:item0")

        node.assertExists()
        item.performClick()

       val handle =composeTestRule
           .onNodeWithTag("handle", useUnmergedTree = true)


        handle.assertExists()
        handle.performTouchInput {
            down(center)
            advanceEventTime(1100)
            moveBy(Offset(0f,
                composeTestRule.activity.resources.displayMetrics.heightPixels.toFloat()))
            up()
        }


        composeTestRule
            .onNodeWithTag("add")
            .performClick()


//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
