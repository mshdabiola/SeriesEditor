/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class, ExperimentalFoundationApi::class)
@Composable
 fun SubjectRoute(
    modifier: Modifier = Modifier,
    subjectId: Long,
) {
    val viewModel: ComposeSubjectViewModel = koinViewModel(parameters = { parametersOf(subjectId) })

//    val feedNote = viewModel.examUiMainState.collectAsStateWithLifecycleCommon()

    SubjectScreen(
        modifier = modifier,
        state = viewModel.state,
        addSubject = viewModel::addSubject
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    state : TextFieldState,
    addSubject: () -> Unit = {},
) {

    Column (
        modifier = modifier
            .testTag("composesubject:screen"),

    ) {

        SeriesEditorTextField(
            modifier=Modifier.testTag("composesubject:subject"),
            state = state,
            label = "Subject",
            placeholder = "Mathematics",
            keyboardAction = {addSubject()},
            maxNum = TextFieldLineLimits.SingleLine

        )
        Spacer(modifier = Modifier.height(8.dp))
        SeriesEditorButton(
            modifier = Modifier.align(Alignment.End),
            onClick = addSubject){
            Text("Add Subject")
        }
    }


}


