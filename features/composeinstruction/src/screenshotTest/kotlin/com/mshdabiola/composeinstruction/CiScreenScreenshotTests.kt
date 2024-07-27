/*
 *abiola 2023
 */

package com.mshdabiola.composeinstruction

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.toImmutableList

class CiScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                CiScreen(
                    modifier = Modifier.fillMaxSize(),
                    ciState = CiState.Loading(),
                    title = rememberTextFieldState(),
                    instructionInput = rememberTextFieldState(),
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                CiScreen(
                    modifier = Modifier.fillMaxSize(),
                    ciState = CiState.Loading(),
                    title = rememberTextFieldState(),
                    instructionInput = rememberTextFieldState(),
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                CiScreen(
                    modifier = Modifier.fillMaxSize(),
                    ciState = CiState.Success(
                        content = listOf(ItemUiState(content = rememberTextFieldState("content"))).toImmutableList(),
                        id = 0,
                        examId = 0,
                    ),
                    title = rememberTextFieldState("title"),
                    instructionInput = rememberTextFieldState(),
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                CiScreen(
                    modifier = Modifier.fillMaxSize(),
                    ciState = CiState.Success(
                        content = listOf(ItemUiState(content = rememberTextFieldState("content"))).toImmutableList(),
                        id = 0,
                        examId = 0,
                    ),
                    title = rememberTextFieldState("title"),
                    instructionInput = rememberTextFieldState(),
                )
            }
        }
    }
}
