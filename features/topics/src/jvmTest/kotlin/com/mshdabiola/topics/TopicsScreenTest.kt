package com.mshdabiola.topics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.topicWithCategory
import com.mshdabiola.ui.toUi
import org.junit.Rule
import kotlin.test.Test

class TopicsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
        TopicScreen(
                modifier = Modifier.fillMaxSize(),
                mainState = Result.Success(
                    topicWithCategory.map { it.toUi() }
                )
            )
        }
        composeRule.onNodeWithTag("topics:screen").assertExists()
        composeRule.onNodeWithTag("topics:list").assertExists()
        composeRule.onNodeWithTag("topics:loading").assertDoesNotExist()
        composeRule.onNodeWithTag("topics:empty").assertDoesNotExist()

    }
}
