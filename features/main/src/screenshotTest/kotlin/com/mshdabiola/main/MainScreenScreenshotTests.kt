/*
 *abiola 2023
 */

package com.mshdabiola.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.seriesmodel.Series

class MainScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = MainState(
                        series = listOf(com.mshdabiola.seriesmodel.Series(1, 6, "ask")),
                    ),
                    subjectState = rememberTextFieldState("subject"),
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = MainState(
                        series = listOf(com.mshdabiola.seriesmodel.Series(1, 6, "ask")),
                    ),
                    subjectState = rememberTextFieldState("subject"),
                )
            }
        }
    }
}
