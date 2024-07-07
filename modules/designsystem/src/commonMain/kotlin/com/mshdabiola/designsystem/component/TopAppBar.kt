/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Deselect
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesEditorTopAppBar(
    titleRes: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = titleRes) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("skTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
    onNavigationClick:( () -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(text = "Series Editor") },
        navigationIcon = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        modifier = Modifier.testTag("back"),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

        },
        colors = colors,
        modifier = modifier.testTag("detailTopAppBar"),
    )
}


@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    isSelectMode: Boolean = false,
    currentSubjectId: Long = 0,
    selectAll: (Long) -> Unit = {},
    deselectAll: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    showExportDialog: () -> Unit = {},
    toggleSelectMode: () -> Unit = {},
    showDeleteDialog: () -> Unit = {},
    updateSubject:(Long)->Unit={},
    onNavigationClick: (() -> Unit)?=null

) {
    var showDrop by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
            title = { Text("Main Screen") },
            navigationIcon ={
                if(onNavigationClick!=null){
                    IconButton( onClick = onNavigationClick){
                        Icon(Icons.Default.Menu,"menu")
                    }
                }
            } ,
            actions = {
                IconButton(
                    onClick = navigateToSetting,
                    // enabled = currentSubjectIndex > -1
                ) {
                    Icon(Icons.Default.Settings, "setting")
                }
                Box {
                    IconButton(
                        onClick = { showDrop = true },
                        // enabled = currentSubjectIndex > -1
                    ) {
                        Icon(Icons.Default.MoreVert, "more")
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
        )

}
