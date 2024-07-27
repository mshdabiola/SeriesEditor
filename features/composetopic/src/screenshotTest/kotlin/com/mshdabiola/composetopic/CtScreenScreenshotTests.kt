/*
 *abiola 2023
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList

class CtScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                CtScreen(
                    modifier = Modifier.fillMaxSize(),
                    ctState = CtState.Loading(),
                    name = rememberTextFieldState("subject"),
                    categoryState = rememberTextFieldState("series"),
                    topicInput = rememberTextFieldState("topic"),

                    )
            }

        }

    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                CtScreen(
                    modifier = Modifier.fillMaxSize(),
                    ctState = CtState.Loading(),
                    name = rememberTextFieldState("subject"),
                    categoryState = rememberTextFieldState("series"),
                    topicInput = rememberTextFieldState("topic"),

                    )
            }
        }

    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {

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
        }

    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {

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
        }

    }


}
