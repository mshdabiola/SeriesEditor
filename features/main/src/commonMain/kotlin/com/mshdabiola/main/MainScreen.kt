/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowColumn
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateBefore
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.automirrored.outlined.Subject
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.designsystem.component.GetFilePath
import com.mshdabiola.designsystem.component.HasWrittenPermission
import com.mshdabiola.designsystem.component.PermissionDialog
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.designsystem.theme.extendedColorScheme
import com.mshdabiola.model.Platform
import com.mshdabiola.model.currentPlatform
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun MainRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToSubject: (Long) -> Unit = {},
) {
    val viewModel: MainViewModel = koinViewModel()

    val update = viewModel.mainState.collectAsStateWithLifecycleCommon()
    val exportState = viewModel.examState.collectAsStateWithLifecycle()

    var deleteId by remember { mutableStateOf<Long?>(null) }

    var hasPermission by remember { mutableStateOf(false) }
    var path by remember { mutableStateOf<String?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showWordDialog by remember { mutableStateOf(false) }


    HasWrittenPermission {
        hasPermission = it
    }
    GetFilePath {
        path = it?.absolutePath
    }
    LaunchedEffect(exportState.value) {
        if (exportState.value is ExportState.Loading) {
            if ((exportState.value as ExportState.Loading).isLoading) {
                showDialog = false
                showWordDialog = false
                delay(500)
                onShowSnack("Export examinations", null)
            }
        }
    }

    MainScreen(
        modifier = modifier,
        subjectState = viewModel.classState,
        mainState = update.value,
        onAdd = viewModel::addClass,
        onDelete = { deleteId = it },
        onUpdate = viewModel::updateClass,
        onClick = navigateToSubject,
        signOut = viewModel::signOut,
        onExport = {
            if (hasPermission) {
                showDialog = true
                viewModel.loadExams()
            } else {
                showPermissionDialog = true
            }
        },
        onExportWord = {
            if (hasPermission) {
                showWordDialog = true
                viewModel.loadExams()
            } else {
                showPermissionDialog = true
            }
        },
    )
    if (deleteId != null) {
        DeleteClassDialog(
            onDismiss = { deleteId = null },
            onConfirm = {
                deleteId?.let(viewModel::deleteClass)
                deleteId = null
            },
        )
    }
    if (showPermissionDialog) {
        PermissionDialog(
            onDismiss = { showPermissionDialog = false },
            onFile = {
                path = it?.absolutePath
                if (it != null) {
                    hasPermission = true
                }
                showPermissionDialog = false
            },
        )
    }

    ExportDialog(
        show = showDialog,
        onDismiss = { showDialog = false },
        exams = exportState.value,
        passwordState = viewModel.passwordState,
        onExport = {
            path?.let(viewModel::onExport)
        },
        onExamSelected = viewModel::onSelect,
    )
    ExportWordDialog(
        show = showWordDialog,
        onDismiss = { showWordDialog = false },
        exams = exportState.value,
        onExport = {
            path?.let(viewModel::onExportWord)
        },
        onExamSelected = viewModel::onSelect,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    mainState: MainState,
    onAdd: () -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    onClick: (Long) -> Unit = {},
    signOut: () -> Unit = {},
    onExport: () -> Unit = {},
    onExportWord: () -> Unit = {},

    ) {
    FlowRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),

        ) {
        FlowRow(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Welcome")
                Text(mainState.user.name, style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = signOut) {
                    Text("SignOut")
                }
            }
            val generalModifier = Modifier.width(150.dp).weight(0.3f)
            MainCard(
                modifier = generalModifier,
                icon = Icons.AutoMirrored.Outlined.Subject,
                color = ListItemDefaults.colors(containerColor = extendedColorScheme.color2.colorContainer),
                title = "Subjects",
                description = mainState.subjectNumber.toString(),
            )

            MainCard(
                modifier = generalModifier,
                icon = Icons.Outlined.Newspaper,
                color = ListItemDefaults.colors(containerColor = extendedColorScheme.color1.colorContainer),
                title = "Examinations",
                description = mainState.examNumber.toString(),
            )

            MainCard(
                modifier = generalModifier,
                icon = Icons.Default.People,
                color = ListItemDefaults.colors(containerColor = extendedColorScheme.color3.colorContainer),
                title = "Students",
                description = "10",
            )
            MainCard(
                modifier = generalModifier,
                icon = Icons.Outlined.Quiz,
                color = ListItemDefaults.colors(containerColor = extendedColorScheme.color4.colorContainer),
                title = "Questions",
                description = mainState.questionNumber.toString(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TextButton(
                    onClick = onExport,
                ) {
                    Text("Export")
                }
                if (currentPlatform != Platform.Android) {

                    TextButton(
                        onClick = onExportWord,
                    ) {
                        Text("Export to Word")
                    }

                }
            }

        }
        Column(
            modifier = Modifier.widthIn(300.dp, 600.dp),
        ) {
            FlowRow(
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                SeriesEditorTextField(
                    modifier = Modifier.weight(1f).testTag("main:class"),
                    state = subjectState,
                    label = "Class",
                    placeholder = "Freshman",
                )

                SeriesEditorButton(
                    modifier = Modifier.testTag("main:add"),
                    onClick = onAdd,
                    enabled = subjectState.text.isNotBlank(),
                ) {
                    Text("Add Class")
                }
            }
            ContextualFlowColumn(
                modifier = Modifier.fillMaxWidth(),
                itemCount = mainState.series.size,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) { index ->
                val series = mainState.series.getOrNull(index)
                if (series != null) {
                    MainCard(
                        series = series,
                        onDelete = onDelete,
                        onUpdate = onUpdate,
                        onClick = onClick,
                    )
                }
            }
        }
    }
}

@Composable
fun MainCard(
    modifier: Modifier = Modifier,
    series: com.mshdabiola.seriesmodel.Series,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    onClick: (Long) -> Unit = {},
) {
    val state = rememberSwipeToDismissBoxState()
    val coroutineScope = rememberCoroutineScope()

    SwipeToDismissBox(
        state = state,
        modifier = modifier,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            ListItem(
                modifier = Modifier,
                headlineContent = {
                },
                trailingContent = {
                    Row {
                        IconButton(onClick = { onDelete(series.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                            )
                        }
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    state.reset()
                                }
                                onUpdate(series.id)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Update,
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    state.reset()
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.NavigateBefore,
                                contentDescription = null,
                            )
                        }
                    }
                },

                )
        },
    ) {
        ListItem(
            modifier = Modifier.clickable {
                onClick(series.id)
            },
            headlineContent = {
                Text(series.name)
            },
            trailingContent = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            state.dismiss(SwipeToDismissBoxValue.StartToEnd)
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.NavigateNext,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}

@Composable
fun MainCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    description: String,
    color: ListItemColors = ListItemDefaults.colors(),
) {
    ListItem(
        modifier = modifier,
        colors = color,
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        headlineContent = {
            Text(text = title, maxLines = 1)
        },
        supportingContent = {
            Text(description)
        },
    )
}

@Composable
fun DeleteClassDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            SeriesEditorButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        title = {
            Text("Delete Class")
        },
        text = {
            Text("Are you sure you want to delete this class?")
        },
    )
}
