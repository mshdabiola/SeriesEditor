/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.ui.QuestionDialog
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
internal fun CiRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: () -> Unit,
    examId: Long,
    instructionId: Long,
) {
    val viewModel: CiViewModel = koinViewModel(parameters = { parametersOf(examId, instructionId) })
    var itemUiState by remember { mutableStateOf<ItemUiState?>(null) }

//    val feedNote = viewModel.examUiMainState.collectAsStateWithLifecycleCommon()
    val update = viewModel.update.collectAsStateWithLifecycleCommon()
    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {
            onFinish()
            onShowSnack("Add Instruction", null)
        }
    }

    CiScreen(
        modifier = modifier,
        instructionUiState = viewModel.instructionUiState.value,
        update = update.value,
        addUp = viewModel::addUp,
        addBottom = viewModel::addDown,
        delete = viewModel::deleteInstruction,
        moveDown = viewModel::moveDown,
        moveUp = viewModel::moveUp,
        changeType = viewModel::changeType,
        onAddInstruction = viewModel::onAdd,
        onChangeView = viewModel::changeView,
        onAddInstructionUiState = viewModel::onAddInstructionInput,
        instructionInput = viewModel.instructionInput,
        onItemClicked = { itemUiState = it },
    )
    QuestionDialog(
        itemUiState = itemUiState,
        onDismiss = { itemUiState = null },
        examId = examId,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CiScreen(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    instructionInput: TextFieldState,
    update: Update,
    addUp: (Int) -> Unit = { _ -> },
    addBottom: (Int) -> Unit = { _ -> },
    delete: (Int) -> Unit = { _ -> },
    moveUp: (Int) -> Unit = { _ -> },
    moveDown: (Int) -> Unit = { _ -> },
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onAddInstruction: () -> Unit = {},
    onAddInstructionUiState: () -> Unit = {},
    onChangeView: (Int) -> Unit = { _ -> },
    onItemClicked: (ItemUiState) -> Unit = {},

) {
    var showConvert by remember { mutableStateOf(false) }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        when (update) {
            Update.Edit -> {
                Section(title = "Instruction Section")
                SeriesEditorTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = instructionUiState.title,
                    maxNum = TextFieldLineLimits.SingleLine,
                    label = "Title",
                )
                Spacer(Modifier.height(4.dp))
                Content(
                    items = instructionUiState.content,
                    label = "Instruction",
                    addUp = addUp,
                    changeView = onChangeView,
                    addBottom = addBottom,
                    delete = delete,
                    moveUp = moveUp,
                    moveDown = moveDown,
                    changeType = changeType,
                    onItemClicked = onItemClicked,
                )
                Spacer(Modifier.height(4.dp))

                SeriesEditorButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onAddInstruction,
                    enabled = instructionUiState.content.any { it.content.text.isNotBlank() },
                    // enabled = instructionUiState.content.first().content.isNotBlank(),
                ) {
                    Text("Add Instruction")
                }

                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .clickable(onClick = { showConvert = !showConvert })
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down",
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        SeriesEditorTextField(
                            state = instructionInput,
//                    isError = instruInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
//                OutlinedTextField(
//                    value = instruInputUiState.content,
//                    onValueChange = onInstruInputChange,
//                    isError = instruInputUiState.isError,
//                    modifier = Modifier.fillMaxWidth().height(300.dp),
//                )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("* instruction title")
                            Button(
                                modifier = Modifier,
                                onClick = onAddInstructionUiState,
                            ) {
                                Text("Convert to Instruction")
                            }
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }

            Update.Saving -> {
                Waiting()
            }

            else -> {}
        }
    }
}
