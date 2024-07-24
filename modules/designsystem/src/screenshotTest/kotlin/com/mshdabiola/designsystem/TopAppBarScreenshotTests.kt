/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.CaptureMultiTheme
import com.mshdabiola.designsystem.component.SeriesEditorTopAppBar
import com.mshdabiola.designsystem.icon.SkIcons

class TopAppBarScreenshotTests() {

    @Preview
    @Composable
    fun TopAppBar() {
        CaptureMultiTheme{
            NiaTopAppBarExample()

        }
    }

    @Preview(fontScale = 2.0f)
    @Composable
    fun TopAppBarHumFontScale2() {
        CaptureMultiTheme {
            NiaTopAppBarExample()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun NiaTopAppBarExample() {
        SeriesEditorTopAppBar(
            titleRes = "untitled",
            navigationIcon = SkIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = SkIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
