package com.mshdabiola.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import java.io.File

@Composable
actual fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit,
) {
//    val scope = rememberCoroutineScope()
//    val context = LocalPlatformContext.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Folder,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            files.firstOrNull()?.let { file ->

                onFile(file.file)
            }
        },
    )
    LaunchedEffect(show) {
        if (show) {
            pickerLauncher.launch()
            onDismiss()
        }
    }
}
