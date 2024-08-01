package com.mshdabiola.composequestion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import kotlin.test.Test

class CqScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            CqScreen(
                modifier = Modifier.fillMaxSize(),
                state = rememberTextFieldState(""),
                cqState = CqState.Success(
                    topics = listOf(
                        TopicUiState(
                            id = 1,
                            topicCategory = TopicCategory(id = 4, name = "name", 4),
                            "title",
                        ),
                    ).toImmutableList(),
                    instructs = listOf(
                        InstructionUiState(
                            id = 1,
                            title = rememberTextFieldState("tittle"),
                            content = listOf(ItemUiState()).toImmutableList(),
                            examId = 1,
                        ),
                    ).toImmutableList(),
                    questionUiState = QuestionUiState(
                        id = 1,
                        number = 1,
                        contents = listOf(ItemUiState(content = rememberTextFieldState("`content`"))).toImmutableList(),
                        options = listOf(
                            OptionUiState(
                                id = 1,
                                nos = 1,
                                content = listOf(ItemUiState(content = rememberTextFieldState("`option`"))).toImmutableList(),
                                isAnswer = false,
                            ),
                            OptionUiState(
                                id = 2,
                                nos = 2,
                                content = listOf(ItemUiState(content = rememberTextFieldState("`option`"))).toImmutableList(),
                                isAnswer = false,
                            ),
                            OptionUiState(
                                id = 3,
                                nos = 3,
                                content = listOf(ItemUiState(content = rememberTextFieldState("`option`"))).toImmutableList(),
                                isAnswer = false,
                            ),
                            OptionUiState(
                                id = 4,
                                nos = 4,
                                content = listOf(ItemUiState(content = rememberTextFieldState("`option`"))).toImmutableList(),
                                isAnswer = false,
                            ),
                        ).toImmutableList(),
                        title = "title",
                        examId = 1,
                        topicUiState = TopicUiState(
                            id = 1,
                            topicCategory = TopicCategory(id = 4, name = "name", 4),
                            "title",
                        ),
                        instructionUiState = InstructionUiState(
                            id = 1,
                            title = rememberTextFieldState("tittle"),
                            content = listOf(ItemUiState()).toImmutableList(),
                            examId = 1,
                        ),
                    ),
                    // errorInInput = false,
                ),
            )
        }
        composeRule.onNodeWithTag("cq:screen").assertExists()
        composeRule.onNodeWithTag("cq:content").assertExists()
        composeRule.onNodeWithTag("cq:add_question").assertExists()
        composeRule.onNodeWithTag("cq:add_answer").assertExists()
    }
}
