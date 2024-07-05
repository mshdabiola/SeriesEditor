/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
internal fun CtRoute(
    modifier: Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    topicId: Long,
    onFinish: () -> Unit,
    subjectId: Long,
) {
    val viewModel: CtViewModel = koinViewModel(parameters = { parametersOf(subjectId, topicId) })

    val update = viewModel.update.collectAsStateWithLifecycleCommon()

    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {

            onFinish()
            onShowSnack("Add Topic", null)
        }
    }

    CtScreen(
        modifier = modifier,
        topicId = topicId,
        update = update.value,
        name = viewModel.state,
        topicInput = viewModel.topicInput,
        onAddTopic = viewModel::addTopic,
        onAddTopicInput = viewModel::addTopicInput,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CtScreen(
    modifier: Modifier = Modifier,
    topicId: Long,
    name: TextFieldState,
    topicInput: TextFieldState,
    onAddTopic: () -> Unit = {},
    onAddTopicInput: () -> Unit = {},
    update: Update,
) {
    var showConvert by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.imePadding()
            .verticalScroll(rememberScrollState()),
    ) {
        when (update) {
            Update.Edit -> {
                val focusRequester = remember {
                    FocusRequester()
                }
                LaunchedEffect(Unit) {
                    delay(1000)
                    focusRequester.requestFocus()

                }
                SeriesEditorTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    state = name,
                    label = "Topic",
                    maxNum = TextFieldLineLimits.SingleLine,
                )

                Spacer(Modifier.height(4.dp))
                SeriesEditorButton(
                    modifier=Modifier.align(Alignment.End),
                    enabled = name.text.isNotBlank(),
                    onClick = onAddTopic) {
                    Text(if (topicId < 0) "Add topic" else "Update topic")
                }
            }

            Update.Saving -> {

                CircularProgressIndicator()
                Text("Saving")

            }

            else -> {

            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            Modifier
                .clickable(onClick = { showConvert = !showConvert })
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
                    state = topicInput,
                    // isError = topicInputUiState.isError,
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                )
//                OutlinedTextField(
//                    value = topicInputUiState.content,
//                    onValueChange = onTopicInputChange,
//                    isError = topicInputUiState.isError,
//                    modifier = Modifier.fillMaxWidth().height(300.dp),
//                )
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("* topic title")
                    Button(
                        modifier = Modifier,
                        onClick = onAddTopicInput,
                    ) {
                        Text("Convert to topic")
                    }
                }
            }
        }
    }


}


