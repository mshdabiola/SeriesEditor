/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun SubjectRoute(
    modifier: Modifier = Modifier,
    seriesId: Long,
    subjectId: Long,
    onFinish: () -> Unit,
    onShowSnack: suspend (String, String?) -> Boolean,

) {
    val viewModel: ComposeSubjectViewModel =
        koinViewModel(parameters = { parametersOf(seriesId, subjectId) })

    val update = viewModel.csState.collectAsStateWithLifecycleCommon()

    LaunchedEffect(update.value) {
        if (update.value is CsState.Loading && (update.value as CsState.Loading).isLoading) {
            onFinish()
            onShowSnack("Add Subject", null)
        }
    }
    SubjectScreen(
        modifier = modifier,
        subjectState = viewModel.subjectState,
        csState = update.value,
        addSubject = viewModel::addSubject,
    )
}

@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    csState: CsState,
    addSubject: () -> Unit = {},
) {
    AnimatedContent(
        targetState = csState,
        modifier = modifier
            .testTag("cs:screen"),

    ) {
        when (it) {
            is CsState.Success -> MainContent(
                modifier = modifier,
                subjectState = subjectState,
                csState = it,
                addSubject = addSubject,
            )

            is CsState.Loading -> {
                Waiting()
            }

            else -> {}
        }
    }
}

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    csState: CsState.Success,
    addSubject: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Section(title = "Subject Section")

        SeriesEditorTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("cs:subject"),
            state = subjectState,
            label = "Subject",
            placeholder = "Mathematics",
            keyboardAction = { addSubject() },
            maxNum = TextFieldLineLimits.SingleLine,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SeriesEditorButton(
            modifier = Modifier.align(Alignment.End).testTag("cs:add_subject"),
            enabled = subjectState.text.isNotBlank(),
            onClick = addSubject,
        ) {
            Icon(Icons.Default.Add, "Add")
            Text("Add Subject")
        }
    }
}
