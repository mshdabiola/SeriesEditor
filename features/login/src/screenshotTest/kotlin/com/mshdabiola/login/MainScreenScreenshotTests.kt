/*
 *abiola 2023
 */

package com.mshdabiola.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.login.LoginScreen

class MainScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    user = TextFieldState(""),
                    password = TextFieldState(""),

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    user = TextFieldState(""),
                    password = TextFieldState(""),

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    user = TextFieldState(""),
                    password = TextFieldState(""),

                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    user = TextFieldState(""),
                    password = TextFieldState(""),

                )
            }
        }
    }
}
