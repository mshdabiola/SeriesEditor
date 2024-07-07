/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.DigitOnlyTransformation
import com.mshdabiola.designsystem.component.MyTextField
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.state.SubjectUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
internal fun ComposeExaminationRoute(
    modifier: Modifier = Modifier,
    examId: Long,
    onBack: () -> Unit,
    onShowSnack: suspend (String, String?) -> Boolean,

) {
    val viewModel: ComposeExaminationViewModel = koinViewModel(
        parameters = {
            parametersOf(examId)
        },
    )

    val subjects = viewModel.subjects.collectAsStateWithLifecycleCommon()
    val update = viewModel.update.collectAsStateWithLifecycleCommon()
    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {
            onBack()
            onShowSnack("Add Examination", null)
        }
    }

    ComposeExaminationScreen(
        modifier = modifier,
        subjects = subjects.value,
        update = update.value,
        subject = viewModel.subject,
        duration = viewModel.duration,
        year = viewModel.year,
        addExam = { viewModel.addExam() },
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun ComposeExaminationScreen(
    modifier: Modifier = Modifier,
    subjects: List<SubjectUiState>,
    update: Update,
    subject: TextFieldState,
    duration: TextFieldState,
    year: TextFieldState,
    addExam: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .testTag("main:screen"),
    ) {
        when (update) {
            Update.Edit -> {
                var expanded by remember { mutableStateOf(false) }

                Section(title = "Examination Section")

                ExposedDropdownMenuBox(
                    modifier = Modifier,
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    MyTextField(
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        state = subject,
                        label = { Text("Subject") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        subjects.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                modifier = Modifier.testTag("dropdown:item$index"),
                                text = { Text(s.name) },
                                onClick = {
                                    subject.clearText()
                                    subject.edit {
                                        append(subjects[index].name)
                                    }
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SeriesEditorTextField(
                        modifier = Modifier
                            .weight(0.5f)
                            .testTag("ce:year"),
                        label = "year",
                        state = year,
                        placeholder = "2014",
                        maxNum = TextFieldLineLimits.SingleLine,
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                        inputTransformation = DigitOnlyTransformation,

                    )
                    SeriesEditorTextField(
                        modifier = Modifier
                            .weight(0.5f)
                            .testTag("ce:duration"),
                        label = "Duration",
                        state = duration,
                        placeholder = "15",
                        maxNum = TextFieldLineLimits.SingleLine,
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                        inputTransformation = DigitOnlyTransformation,

                    )
                }

                SeriesEditorButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .testTag("ce:add_exam"),
                    onClick = {
                        addExam()
                    },
                    enabled = subject.text.toString().isNotBlank() &&
                        duration.text.toString().isNotBlank() &&
                        year.text.toString().isNotBlank(),
                ) {
                    Icon(Icons.Default.Add, "add")
                    Text("Add Examination")
                }
            }

            Update.Saving -> {
                Waiting()
            }

            else -> {}
        }
    }
}
