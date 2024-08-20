/*
 *abiola 2023
 */

package com.mshdabiola.composeexam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme

class CeScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                ComposeExaminationScreen(
                    modifier = Modifier.fillMaxSize(),
                    ceState = CeState.Loading(),
                    duration = rememberTextFieldState(),
                    year = rememberTextFieldState(),
                    addExam = {},

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                ComposeExaminationScreen(
                    modifier = Modifier.fillMaxSize(),
                    ceState = CeState.Loading(),
                    duration = rememberTextFieldState(),
                    year = rememberTextFieldState(),
                    addExam = {},

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                ComposeExaminationScreen(
                    modifier = Modifier.fillMaxSize(),
                    ceState = CeState.Success(isUpdate = false),
                    duration = rememberTextFieldState("12"),
                    year = rememberTextFieldState("1556"),
                    addExam = {},

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                ComposeExaminationScreen(
                    modifier = Modifier.fillMaxSize(),
                    ceState = CeState.Success(isUpdate = false),
                    duration = rememberTextFieldState("12"),
                    year = rememberTextFieldState("1556"),
                    addExam = {},

                )
            }
        }
    }
}
