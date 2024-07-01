package com.mshdabiola.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.component.DeleteDialog
import com.mshdabiola.designsystem.component.MainExportDialog
import com.mshdabiola.designsystem.component.MainTopBar
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parameterArrayOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainTopBarSection(
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit,
    currentSubjectId:Long,

    ) {
    val viewModel: MainViewModel = koinViewModel(parameters = { parameterArrayOf(currentSubjectId) })

    var deleteState by remember { mutableStateOf<DeleteState?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    MainTopBar(
        modifier = modifier,
        isSelectMode = viewModel.mainState.value.isSelectMode,
        currentSubjectId = viewModel.mainState.value.currentSubjectId,
        selectAll = viewModel::selectAll,
        selectAllSubject = viewModel::selectAllSubject,
        deselectAll = viewModel::deselectAll,
        navigateToSetting = navigateToSetting,
        showExportDialog = { showDialog = true },
        toggleSelectMode = viewModel::toggleSelectMode,
        showDeleteDialog = {},
    )

    MainExportDialog(
        show = showDialog,
        export = viewModel::onExport,
        onClose = { showDialog = false },
    )

    DeleteDialog(
        show = deleteState != null,
        onDismiss = {

        },
        onDelete = {

            when (deleteState) {
                null -> TODO()
                DeleteState.All -> {
                    viewModel.deleteSelected()
                }

                is DeleteState.Id -> viewModel.onDeleteExam((deleteState as DeleteState.Id).id)
            }
            deleteState = null
        },
    )


}


sealed class DeleteState {
    data object All : DeleteState()
    data class Id(val id: Long) : DeleteState()
}



