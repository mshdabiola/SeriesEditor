/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.CaptureMultiTheme
import com.mshdabiola.designsystem.component.SkTab
import com.mshdabiola.designsystem.component.SkTabRow

class TabsScreenshotTests {

    @Preview
    @Composable
    fun Tabs() {
        CaptureMultiTheme {
            NiaTabsExample()
        }
    }

    @Preview
    @Preview(fontScale = 2.0f)
    @Composable
    fun TabsHumFontScale2() {
        CaptureMultiTheme {
            NiaTabsExample("Looooong item")
        }
    }

    @Composable
    private fun NiaTabsExample(label: String = "Topics") {
        Surface {
            val titles = listOf(label, "People")
            SkTabRow(selectedTabIndex = 0) {
                titles.forEachIndexed { index, title ->
                    SkTab(
                        selected = index == 0,
                        onClick = { },
                        text = { Text(text = title) },
                    )
                }
            }
        }
    }
}
