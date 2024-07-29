/*
 *abiola 2023
 */

package com.mshdabiola.topics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.testing.instructions
import com.mshdabiola.ui.toInstructionUiState
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.examinations
import com.mshdabiola.testing.topicWithCategory
import com.mshdabiola.ui.toUi

class ScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                TopicScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Loading
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                TopicScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Loading
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                TopicScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Success(
                        topicWithCategory.map { it.toUi() }
                    )
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                TopicScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Success(
                        topicWithCategory.map { it.toUi() }
                    )
                )
            }
        }
    }
}
