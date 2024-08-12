package com.mshdabiola.serieseditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.component.DeleteDialog
import com.mshdabiola.designsystem.component.GetFilePath
import com.mshdabiola.designsystem.component.HasWrittenPermission
import com.mshdabiola.designsystem.component.MainExportDialog
import com.mshdabiola.designsystem.component.MainTopBar
import com.mshdabiola.designsystem.component.PermissionDialog
import com.mshdabiola.designsystem.component.SeBottonAppBar
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainTopBarSection(
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
    subjectId: Long,
    updateSubject: (Long) -> Unit,
    onNavigationClick: (() -> Unit)? = null,
    onAddTopic: (() -> Unit)? = null,

) {
    val viewModel: MainAppViewModel = koinViewModel()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val isSelect = viewModel.isSelectMode.collectAsStateWithLifecycleCommon()

    var path by remember { mutableStateOf<String?>(null) }
    var hasPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    HasWrittenPermission {
        hasPermission = it
    }

    GetFilePath {
        path = it?.absolutePath
    }

    MainTopBar(
        modifier = modifier,
        isSelectMode = isSelect.value,
        currentSubjectId = subjectId,
        selectAll = viewModel::selectAll,
        deselectAll = viewModel::deselectAll,
        navigateToSetting = navigateToSetting,
        showExportDialog = {
            if (hasPermission) {
                showDialog = true
            } else {
                showPermissionDialog = true
            }
        },
        exportWord = { viewModel.onExportWord(path!!) },
        toggleSelectMode = viewModel::toggleSelectMode,
        showDeleteDialog = { showDeleteDialog = true },
        updateSubject = updateSubject,
        onNavigationClick = onNavigationClick,
    )

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
    MainExportDialog(
        show = showDialog,
        export = { viewModel.onExport(path!!, it) },
        onClose = { showDialog = false },
    )

    DeleteDialog(
        show = showDeleteDialog,
        onDismiss = {
            showDeleteDialog = false
        },
        onDelete = {
            viewModel.deleteSelected()
            showDeleteDialog = false
        },
    )
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainBottomBarSection(
    modifier: Modifier = Modifier,
    appState: Other,
    subjectId: Long,
    onNavigationClick: (() -> Unit)?,
    fabText: String,

) {
    val viewModel: MainAppViewModel = koinViewModel()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val isSelect = viewModel.isSelectMode.collectAsStateWithLifecycleCommon()

    var path by remember { mutableStateOf<String?>(null) }
    var hasPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    HasWrittenPermission {
        hasPermission = it
    }

    GetFilePath {
        path = it?.absolutePath
    }

    SeBottonAppBar(
        modifier = modifier,
        isSelectMode = isSelect.value,
        fabText = fabText,
        currentSubjectId = subjectId,
        selectAll = viewModel::selectAll,
        deselectAll = viewModel::deselectAll,
        showExportDialog = {
            if (hasPermission) {
                showDialog = true
            } else {
                showPermissionDialog = true
            }
        },
        toggleSelectMode = viewModel::toggleSelectMode,
        showDeleteDialog = { showDeleteDialog = true },
        onFabClick =
        if (appState.isList) {
            {
                appState.onAdd()
            }
        } else {
            null
        },
        onNavigationClick = onNavigationClick,
        onSettingsClick = if (appState.isMain) {
            { appState.navController.navigateToSetting() }
        } else {
            null
        },
        exportWord = { viewModel.onExportWord(path!!) },
        onBackClick = if (!appState.isMain) {
            { appState.navController.popBackStack() }
        } else {
            null
        },
    )
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
    MainExportDialog(
        show = showDialog,
        export = { viewModel.onExport(path!!, it) },
        onClose = { showDialog = false },
    )

    DeleteDialog(
        show = showDeleteDialog,
        onDismiss = {
            showDeleteDialog = false
        },
        onDelete = {
            viewModel.deleteSelected()
            showDeleteDialog = false
        },
    )
}

sealed class DeleteState {
    data object All : DeleteState()
    data class Id(val id: Long) : DeleteState()
}
