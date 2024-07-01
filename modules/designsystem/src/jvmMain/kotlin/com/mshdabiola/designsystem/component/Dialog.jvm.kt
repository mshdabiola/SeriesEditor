package com.mshdabiola.designsystem.component

import androidx.compose.runtime.Composable
import java.io.File

@Composable
actual fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit
) {
}