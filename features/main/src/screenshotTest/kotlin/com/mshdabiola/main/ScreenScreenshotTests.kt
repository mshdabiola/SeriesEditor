/*
 *abiola 2023
 */

package com.mshdabiola.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.testing.examinations
import com.mshdabiola.ui.toUi

class ScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Loading,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Loading,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = Result.Success(
                        examinations.map { it.toUi() },
                    ),
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
                    mainState = Result.Success(
                        examinations.map { it.toUi() },
                    ),
                )
            }
        }
    }
}
