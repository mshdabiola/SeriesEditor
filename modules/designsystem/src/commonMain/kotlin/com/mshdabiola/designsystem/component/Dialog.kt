package com.mshdabiola.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.io.File

@Composable
fun MainExportDialog(
    show: Boolean,
    export: (String, String) -> Unit = { _, _ -> },
    onClose: () -> Unit = {},
) {

    var path by remember { mutableStateOf<String?>(null) }

    val key = rememberTextFieldState()
    var hasPermission by remember { mutableStateOf(false) }


    if (show) {
        HasWrittenPermission {
            hasPermission = it
        }

        GetFilePath {
            path = it?.absolutePath

        }

        if (!hasPermission) {
            PermissionDialog(
                onDismiss = onClose,
                onFile = {
                    path = it?.absolutePath
                    if (it != null) {
                        hasPermission = true
                    }
                },
            )
        } else {
            AlertDialog(
                onDismissRequest = onClose,
                dismissButton = {
                    TextButton(onClick = onClose) {
                        Text("Cancel")
                    }

                },
                confirmButton = {
                    Button(
                        onClick = {
                            onClose()
                            export(path!!, key.text.toString())
                        },
                    ) {
                        Text("Export")
                    }
                },
                icon = { Icon(Icons.Default.Password, "password") },
                title = { Text(text = "Enter password") },
                text = {
                    SeriesEditorTextField(
                        state = key,
                        label = "Password",
                    )
                },
            )

        }

    }

}

@Composable
fun DeleteDialog(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    onDelete: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    if (show) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                ElevatedButton(
                    onClick = onDelete,

                    ) {
                    Text("Delete exam")
                }
            },
            icon = { Icon(Icons.Default.Delete, "delete") },
            title = { Text(text = "Delete Subject") },
            text = {
                Text("Are you sure you want to delete this examination?")
            },
        )
    }
}

@Composable
expect fun GetFilePath(
    onFile: (File?) -> Unit = {},
)

@Composable
expect fun HasWrittenPermission(result: (Boolean) -> Unit)

@Composable
expect fun PermissionDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onFile: (File?) -> Unit = {},
)
