/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.state.QuestionUiState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun CqRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: ()->Unit,
    examId: Long,
    questionId:Long
) {
    val viewModel: CqViewModel = koinViewModel(parameters = { parametersOf(examId,questionId) })

//    val feedNote = viewModel.examUiMainState.collectAsStateWithLifecycleCommon()
    val update = viewModel.update.collectAsStateWithLifecycleCommon()
    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {
            onShowSnack("Add Question",null)
            onFinish()
        }
    }
    CqScreen(
        modifier = Modifier,
        questionUiState = viewModel.question.value,
        update=update.value,
        addUp =viewModel::addUP,
        addBottom = viewModel::addDown,
        delete = viewModel::delete,
        moveUp = viewModel::moveUP,
        moveDown = viewModel::moveDown,
        changeType = viewModel::changeType,
        onAddQuestion = viewModel::onAddQuestion,
        // onTextChange = viewModel::onTextChange,
         onAddOption = viewModel::onAddOption,
        // onAddAnswer = viewModel::onAddAnswer,
         isTheory = viewModel::isTheory,
        changeView = viewModel::changeView
        //

    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun CqScreen(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    update: Update,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    changeView: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
//onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> },
    fillIt: Boolean = false,
    onAddQuestion: () -> Unit = {},
    onAddOption: () -> Unit = {},
    onAddAnswer: (Boolean) -> Unit = {},
    isTheory: (Boolean) -> Unit = {},
) {

    var showTopiDropdown by remember { mutableStateOf(false) }
    var showConvert by remember { mutableStateOf(false) }
    val state = rememberTextFieldState()

//    var fillIt =
//        rememberUpdatedState(screenSize != ScreenSize.EXPANDED)

    Column(
        modifier = modifier
            .testTag("cq:screen"),

        ) {
        when(update){
            Update.Edit->{
                Content(
                    items = questionUiState.contents,
                    examId = questionUiState.examId,
                    label = "Question",
                    addUp = { addUp(-1, it) },
                    addBottom = { addBottom(-1, it) },
                    delete = { delete(-1, it) },
                    moveUp = { moveUp(-1, it) },
                    moveDown = { moveDown(-1, it) },
                    changeView = { changeView(-1, it) },
                    changeType = { i, t -> changeType(-1, i, t) },
                    // onTextChange = { i, s -> onTextChange(-1, i, s) },

                )

                FlowRow(modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 2,
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                  //  horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.CenterHorizontally)
                ) {
                    questionUiState.options?.forEachIndexed { i, optionUiState ->

//                Box(modifier=Modifier.height(20.dp).fillMaxWidth(0.49f).background(if(i%2==0)Color.Blue else Color.Black))

                        Content(
                            modifier = Modifier.fillMaxWidth(if (fillIt) 1f else 0.499999f), // .weight(0.5f)
                            items = optionUiState.content,
                            label = "Option ${i+1}",
                            examId = questionUiState.examId,
                            addUp = { addUp(i, it) },
                            addBottom = { addBottom(i, it) },
                            delete = { delete(i, it) },
                            moveUp = { moveUp(i, it) },
                            moveDown = { moveDown(i, it) },
                             changeView = { changeView(i, it) },
                            changeType = { ii, t -> changeType(i, ii, t) },
                            // onTextChange = { idn, s -> onTextChange(i, idn, s) },

                        )
                    }
                }

                if (questionUiState.answers != null && questionUiState.answers!!.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text("Answer", modifier = Modifier.padding(horizontal = 16.dp))
                    Content(
                        items = questionUiState.answers!!,
                        examId = questionUiState.id,
                        label = "Answer",
                        addUp = { addUp(-2, it) },
                        addBottom = { addBottom(-2, it) },
                        delete = { delete(-2, it) },
                        moveUp = { moveUp(-2, it) },
                        moveDown = { moveDown(-2, it) },
                        changeView = { changeView(-2, it) },
                        changeType = { i, t -> changeType(-2, i, t) },
                        //onTextChange = { i, s -> onTextChange(-2, i, s) },

                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    ){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Is Theory")
                        Spacer(Modifier.width(4.dp))
                        Switch(
                            checked = questionUiState.isTheory,
                            onCheckedChange = { isTheory(it) },
                            enabled = questionUiState.id < 0,
                        )
                    }

                    SeriesEditorButton(modifier = Modifier.testTag("question:add_question"), onClick = onAddQuestion) {
                        Icon(Icons.Default.Add, "add")
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Add Question")
                    }
                }
                FlowRow(
                    Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SuggestionChip(
                        onClick = onAddOption,
                        label = { Text("Add Option") },
                    )

                    //TODO("fix answer not null")
                    SuggestionChip(
                        onClick = { onAddAnswer(questionUiState.answers == null) },
                        label = {
                            Text(
                                if (questionUiState.answers == null) "Add Answers" else "Remove answer",
                            )
                        },
                    )
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
                        SeriesEditorTextField(
                           state = state,
                           // isError = examInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("*q* question *o* option *t* type 0or1 *a* answer")
                            Button(
                                modifier = Modifier,
                                onClick = {},
                            ) {
                                Text("Convert to Exam")
                            }
                        }

                }
                Spacer(Modifier.height(16.dp))
                //TemplateUi()
            }
            Update.Saving->{

                CircularProgressIndicator()
                Text("Saving")


            }
            else->{}
        }


    }


}


