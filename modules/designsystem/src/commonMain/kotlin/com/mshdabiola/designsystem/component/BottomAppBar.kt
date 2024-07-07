package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Deselect
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun SeBottonAppBar(
    modifier: Modifier = Modifier,
    onFabClick: (() -> Unit)? = null,
    onNavigationClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    isSelectMode: Boolean = false,
    currentSubjectId: Long = -1,
    selectAll: (Long) -> Unit = {},
    deselectAll: () -> Unit = {},
    showExportDialog: () -> Unit = {},
    toggleSelectMode: () -> Unit = {},
    showDeleteDialog: () -> Unit = {},
    updateSubject:(Long)->Unit={},
) {
    var showDrop by remember { mutableStateOf(false) }

    BottomAppBar(
        modifier = modifier,
        floatingActionButton = {
            if (onFabClick != null) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.testTag("add"),
                    onClick = onFabClick,
                ) {
                    Icon(Icons.Default.Add, "add")
                    Spacer(Modifier.width(8.dp))
                    Text("Add")
                }

            }
        },
        actions = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(Icons.Default.Menu, "menu")
                }
            }
            if (onSettingsClick != null) {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, "setting")
                }
                Box {
                    IconButton(
                        onClick = { showDrop = true },
                        // enabled = currentSubjectIndex > -1
                    ) {
                        Icon(Icons.Default.MoreHoriz, "more")
                    }

                    if (isSelectMode) {
                        DropdownMenu(
                            expanded = showDrop,
                            onDismissRequest = { showDrop = false },
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.SelectAll,
                                        "select All",
                                    )
                                },
                                text = { Text("Select All") },
                                onClick = {
                                    selectAll(currentSubjectId)
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        Icons.Rounded.Deselect,
                                        "deselect",
                                    )
                                },
                                text = { Text("Deselect All") },
                                onClick = {
                                    deselectAll()
                                    showDrop = false
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        Icons.Rounded.SaveAs,
                                        "save",
                                    )
                                },
                                text = { Text("Export Selected") },
                                onClick = {
                                    // onDelete(examUiState.id)
                                    showExportDialog()
                                    showDrop = false
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        Icons.Rounded.Delete,
                                        "delete",
                                    )
                                },
                                text = { Text("Delete selected") },
                                onClick = {
                                    showDeleteDialog()
                                    showDrop = false
                                },
                            )
                        }
                    } else {
                        DropdownMenu(
                            expanded = showDrop,
                            onDismissRequest = { showDrop = false },
                        ) {
                            if (currentSubjectId > -1) {
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Update,
                                            "update",
                                        )
                                    },
                                    text = { Text("Update") },
                                    onClick = {
                                        updateSubject(currentSubjectId)
                                        showDrop = false
                                    },
                                )
                            }

                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.HdrOnSelect,
                                        "select mode",
                                    )
                                },
                                text = { Text("Select mode") },
                                onClick = {
                                    toggleSelectMode()
                                    showDrop = false
                                },
                            )
                        }
                    }
                }
            }
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBackIosNew, "back")
                }

            }



        },
    )
}
