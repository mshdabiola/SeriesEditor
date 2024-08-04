package com.mshdabiola.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    isEmpty: Boolean=true,
    onPathChange: (String) -> Unit,
)
