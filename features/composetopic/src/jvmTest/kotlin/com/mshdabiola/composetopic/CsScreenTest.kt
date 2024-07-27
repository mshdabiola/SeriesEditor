package com.mshdabiola.composetopic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import kotlin.test.Test

class CsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            CtScreen(
                modifier = Modifier.fillMaxSize(),
                ctState = CtState.Success(
                    categories = listOf(),
                    currentCategoryIndex = 7,
                    topicId = 1,
                ),
                name = rememberTextFieldState("subject"),
                categoryState = rememberTextFieldState("series"),
                topicInput = rememberTextFieldState("topic"),

                )

        }
       composeRule.onNodeWithTag("ct:screen").assertExists()
        composeRule.onNodeWithTag("ct:previous").assertExists()
        composeRule.onNodeWithTag("ct:next").assertExists()
        composeRule.onNodeWithTag("ct:list_categories").assertExists()
        composeRule.onNodeWithTag("ct:topic").assertExists()
        composeRule.onNodeWithTag("ct:add_topic").assertExists()
        composeRule.onNodeWithTag("ct:category").assertExists()
        composeRule.onNodeWithTag("ct:add_category").assertExists()
        composeRule.onNodeWithTag("ct:delete_category").assertExists()



    }
}
