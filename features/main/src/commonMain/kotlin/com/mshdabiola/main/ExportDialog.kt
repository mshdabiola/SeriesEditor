package com.mshdabiola.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.component.GetFilePath
import com.mshdabiola.designsystem.component.HasWrittenPermission
import com.mshdabiola.designsystem.component.MainExportDialog
import com.mshdabiola.designsystem.component.PermissionDialog
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.designsystem.component.scrollbar.DraggableScrollbar
import com.mshdabiola.designsystem.component.scrollbar.rememberDraggableScroller
import com.mshdabiola.designsystem.component.scrollbar.scrollbarState
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Dialog for exporting data with password protection.
 *
 * @param show Whether the dialog should be displayed.
 * @param passwordState The state of the password text field.
 * @param exams The list of exams available for export.
 * @param onExamSelected Function to be called when an exam is selected/deselected.
 * @param onExport Function to be called with the entered password when the "Export" button is clicked.
 * @param onDismiss Function to be called when the dialog is dismissed.
 */
@Composable
fun ExportDialog(
    show: Boolean,
    passwordState: TextFieldState,
    exams: ExportState,
    onExamSelected: (Long) -> Unit,
    onExport: () -> Unit = { },
    onDismiss: () -> Unit = {},
) {

    if (show) {
        when (exams) {
            is ExportState.Loading -> {
                AlertDialog(
                    text = {
                        Box(
                            modifier = Modifier.height(180.dp).fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    onDismissRequest = {},
                    dismissButton = {

                    },
                    confirmButton = {

                    },
                )
            }

            is ExportState.Success -> {
                val state = rememberLazyStaggeredGridState()

                AlertDialog(

                    onDismissRequest = onDismiss,
                    dismissButton = {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                onExport()
                                onDismiss() // Dismiss after export
                            },
                        ) {
                            Text("Export")
                        }
                    },
                    icon = { Icon(Icons.Default.Password, contentDescription = "password") },
                    title = { Text("Export with Password") }, // More descriptive title
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            SeriesEditorTextField(
                                modifier = Modifier.fillMaxWidth(),
                                state = passwordState,
                                label = "Password",
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Add spacing for readability
                            Text("Select Exams to Export")
                            Box {
                                LazyHorizontalStaggeredGrid(
                                    state = state,
                                    modifier = Modifier.height(180.dp),
                                    rows = StaggeredGridCells.Fixed(2),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalItemSpacing = 8.dp,
                                ) {

                                    items(exams.exams, key = { it.id }) { exam ->
                                        ExamItem(
                                            exam = exam,
                                            onSelected = onExamSelected,
                                        )
                                    }
                                }
                                val itemsAvailable = exams.exams.size
                                val scrollbarState = state.scrollbarState(
                                    itemsAvailable = itemsAvailable,
                                )
                                state.DraggableScrollbar(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .windowInsetsPadding(WindowInsets.systemBars)
                                        .padding(horizontal = 2.dp)
                                        .align(Alignment.BottomEnd),
                                    state = scrollbarState,
                                    orientation = Orientation.Horizontal,
                                    onThumbMoved = state.rememberDraggableScroller(
                                        itemsAvailable = itemsAvailable,
                                    ),
                                )
                            }
                        }
                    },
                )
            }

            else -> {}
        }

    }
}

@Composable
fun ExamItem(
    exam: ExamState,
    onSelected: (Long) -> Unit,
) {
    val color = if (exam.isSelected) {
        ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    } else {
        ListItemDefaults.colors()
    }
    ListItem(
        modifier = Modifier
            .clickable { onSelected(exam.id) },
        colors = color,
        overlineContent = { Text("${exam.classRoom} - ${exam.year}") },
        headlineContent = { Text(exam.subject) },
    )
}