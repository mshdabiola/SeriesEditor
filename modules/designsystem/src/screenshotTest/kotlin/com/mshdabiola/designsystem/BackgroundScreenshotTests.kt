/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.Capture
import com.mshdabiola.designsystem.component.SeriesEditorBackground

class BackgroundScreenshotTests {

    @Preview
    @Composable
    fun Background() {
        Capture {
            SeriesEditorBackground(Modifier.size(100.dp)) {
                Text("background")
            }
        }
    }

    @Preview
    @Composable
    fun GradientBackground() {
        Capture {
            SeriesEditorBackground(Modifier.size(100.dp)) {
                Text("Gradient background")
            }
        }
    }
}
