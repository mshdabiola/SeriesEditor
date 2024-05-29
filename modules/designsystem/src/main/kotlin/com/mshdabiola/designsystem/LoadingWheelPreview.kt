package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SeriesEditorLoadingWheel
import com.mshdabiola.designsystem.component.SkOverlayLoadingWheel
import com.mshdabiola.designsystem.theme.SeriesEditorTheme

@ThemePreviews
@Composable
fun NiaLoadingWheelPreview() {
    SeriesEditorTheme {
        Surface {
            SeriesEditorLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

@ThemePreviews
@Composable
fun NiaOverlayLoadingWheelPreview() {
    SeriesEditorTheme {
        Surface {
            SkOverlayLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}
