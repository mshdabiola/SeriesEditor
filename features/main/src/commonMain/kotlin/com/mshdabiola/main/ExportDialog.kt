package com.mshdabiola.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.component.GetFilePath
import com.mshdabiola.designsystem.component.HasWrittenPermission
import com.mshdabiola.designsystem.component.MainExportDialog
import com.mshdabiola.designsystem.component.PermissionDialog
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainTopBarSection(
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var path by remember { mutableStateOf<String?>(null) }
    var hasPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    HasWrittenPermission {
        hasPermission = it
    }

    GetFilePath {
        path = it?.absolutePath
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
    MainExportDialog(
        show = showDialog,
        export = {}, // { viewModel.onExport(path!!, it) },
        onClose = { showDialog = false },
    )
}
