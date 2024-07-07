/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
internal fun SubjectRoute(
    modifier: Modifier = Modifier,
    subjectId: Long,
    onFinish: () -> Unit,
    onShowSnack: suspend (String, String?) -> Boolean,

    ) {
    val viewModel: ComposeSubjectViewModel = koinViewModel(parameters = { parametersOf(subjectId) })

    val update = viewModel.update.collectAsStateWithLifecycleCommon()

    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {

            onFinish()
            onShowSnack("Add Subject", null)
        }
    }
    SubjectScreen(
        modifier = modifier,
        state = viewModel.state,
        update = update.value,
        addSubject = viewModel::addSubject,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    update: Update,
    addSubject: () -> Unit = {},
) {

    Column(
        modifier = modifier

            .testTag("composesubject:screen"),

        ) {

        when (update) {
            Update.Edit -> {
                Section(title = "Subject Section")

                SeriesEditorTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("composesubject:subject"),
                    state = state,
                    label = "Subject",
                    placeholder = "Mathematics",
                    keyboardAction = { addSubject() },
                    maxNum = TextFieldLineLimits.SingleLine,

                    )
                Spacer(modifier = Modifier.height(8.dp))
                SeriesEditorButton(
                    modifier = Modifier.align(Alignment.End),
                    enabled =state.text.isNotBlank(),
                    onClick = addSubject,
                ) {
                    Icon(Icons.Default.Add,"Add")
                    Text("Add Subject")
                }
            }

            Update.Saving -> {

                Waiting()


            }

            else -> {}
        }

    }


}


