/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.CaptureMultiTheme
import com.mshdabiola.designsystem.component.SeriesEditorLoadingWheel
import com.mshdabiola.designsystem.component.SkOverlayLoadingWheel

class LoadingWheelScreenshotTests {

    @Preview
    @Composable
    fun LoadingWheel() {
        CaptureMultiTheme {
            SeriesEditorLoadingWheel(contentDesc = "test")
        }
    }

    @Preview
    @Composable
    fun OverlayLoadingWheel() {
        CaptureMultiTheme {
            SkOverlayLoadingWheel(contentDesc = "test")
        }
    }
}
