package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.CaptureMultiTheme

@Preview
@Composable
actual fun ProfileCardPreview() {
    CaptureMultiTheme {
        ProfileCard()
    }
}
