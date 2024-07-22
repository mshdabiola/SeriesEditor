package com.mshdabiola.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.io.File
import java.io.OutputStream

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainExportDialog(
    show: Boolean,
    export: (OutputStream, String) -> Unit = { _, _ -> },
    onClose: () -> Unit = {},
) {
    var showDir by remember {
        mutableStateOf(false)
    }
    var path = rememberTextFieldState()

    val key = rememberTextFieldState()

    if (show) {
        Dialog(onDismissRequest = onClose, properties = DialogProperties()) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Export Examination",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    IconButton(onClick = { showDir = true }) {
                        Icon(
                            modifier = Modifier.weight(0.1f),
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "select",
                        )
                    }

                    SeriesEditorTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = key,
                        label = "key",
                        placeholder = "SwordFish",
                        imeAction = ImeAction.Next,
                        maxNum = TextFieldLineLimits.SingleLine,
                    )

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        TextButton(onClick = onClose) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                val file = File(path.text.toString(), "data.se")
                                if (file.parentFile?.exists() == false) {
                                    file.parentFile?.mkdirs()
                                }
                                if (!file.exists()) {
                                    file.createNewFile()
                                }
                                export(
                                    file.outputStream(),
                                    key.text.toString().ifBlank { "SwordFish" },
                                )

                                key.clearText()
                                onClose()
                            },
                            enabled = path.text.isNotBlank(),
                        ) {
                            Text("Export")
                        }
                    }
                }
            }
        }
    }
    DirtoryUi(showDir, onDismiss = { showDir = false }) {
        it?.let {
            path.edit {
                this.append(it.path)
            }
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
expect fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit = {},
    onFile: (File?) -> Unit = {},
)
