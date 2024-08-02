package com.mshdabiola.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import java.io.File

@Composable
actual fun GetFilePath(
    onFile: (File?) -> Unit,
) {
    LaunchedEffect(Unit) {

        onFile(File(getDesktopPath(), "series"))

    }
}

fun getDesktopPath(): String {
    val userHome = System.getProperty("user.home")
    return when {
        System.getProperty("os.name").startsWith("Windows") -> "$userHome\\Desktop"
        System.getProperty("os.name").startsWith("Mac OS X") -> "$userHome/Desktop"
        else -> "$userHome/Desktop" // Linux (and other Unix-like systems)
    }
}

@Composable
actual fun PermissionDialog(
    modifier: Modifier,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit,
) {
}

@Composable
actual fun HasWrittenPermission(result: (Boolean) -> Unit) {
    LaunchedEffect(Unit){
        result(true)
    }
}