/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToString
import com.mshdabiola.detail.question.QuestionEditUi
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_showsLoadingSpinner() {
        composeTestRule.setContent {
            QuestionEditUi(
                questionUiState = QuestionUiState(
                    id = 1,
                    number = 1,
                    examId = 1,
                    contents = listOf(ItemUiState("question", isEditMode = true)).toImmutableList(),
                    options = List(4) {
                        OptionUiState(
                            id = it.toLong(), nos = it.toLong(),
                            content = listOf(ItemUiState("question", isEditMode = true)).toImmutableList(),
                            isAnswer = false,
                        )
                    }.toImmutableList(),
                ),
            )
        }

       val node= composeTestRule.onAllNodesWithTag("content")

     //   println(node.printToString(6))

        node.assertCountEquals(5)

        node[0].performTextInput("Question")
        composeTestRule.waitForIdle()
        node[1].performTextInput("Option 1")
        composeTestRule.waitForIdle()
        node[2].performTextInput("Option 2")
        composeTestRule.waitForIdle()
        node[3].performTextInput("Option 3")
        composeTestRule.waitForIdle()
        node[4].performTextInput("Option 4")
        composeTestRule.waitForIdle()

    }
}
