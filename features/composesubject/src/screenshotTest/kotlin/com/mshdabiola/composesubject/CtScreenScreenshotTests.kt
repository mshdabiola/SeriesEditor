/*
 *abiola 2023
 */

package com.mshdabiola.composesubject

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
                SubjectScreen(
                    modifier = Modifier.fillMaxSize(),
                    csState = CsState.Loading(),
                    subjectState = rememberTextFieldState("subject"),
                    seriesState = rememberTextFieldState("series"),
                )
            }

        }

    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                SubjectScreen(
                    modifier = Modifier.fillMaxSize(),
                    csState = CsState.Loading(),
                    subjectState = rememberTextFieldState("subject"),
                    seriesState = rememberTextFieldState("series"),
                )
            }
        }

    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {

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
        }

    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {

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
        }

    }


}
