/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.CaptureMultiTheme
import com.mshdabiola.designsystem.component.SkTopicTag

class TagScreenshotTests {

    @Preview
    @Composable
    fun Tag() {
        CaptureMultiTheme {
            SkTopicTag(followed = true, onClick = {}) {
                Text("TOPIC")
            }
        }
    }

    @Preview
    @Composable
    fun TagHumFontScale2() {
        CaptureMultiTheme {
            SkTopicTag(followed = true, onClick = {}) {
                Text("LOOOOONG TOPIC")
            }
        }
    }
}
