package com.mshdabiola.composesubject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import kotlin.test.Test

class CsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            SubjectScreen(
                modifier = Modifier.fillMaxSize(),
                csState = CsState.Success(
                   series = listOf(Series(1,6,"ask")),
                    currentSeries = 1,
                ),
                subjectState = rememberTextFieldState("subject"),
                seriesState = rememberTextFieldState("series"),
            )

        }
       composeRule.onNodeWithTag("cs:screen").assertExists()
        composeRule.onNodeWithTag("cs:list_series").assertExists()
        composeRule.onNodeWithTag("cs:previous").assertExists()
        composeRule.onNodeWithTag("cs:next").assertExists()
        composeRule.onNodeWithTag("cs:subject").assertExists()
        composeRule.onNodeWithTag("cs:series").assertExists()
        composeRule.onNodeWithTag("cs:add_series").assertExists()
        composeRule.onNodeWithTag("cs:add_subject").assertExists()
        composeRule.onNodeWithTag("cs:delete_series").assertDoesNotExist()


    }
}
