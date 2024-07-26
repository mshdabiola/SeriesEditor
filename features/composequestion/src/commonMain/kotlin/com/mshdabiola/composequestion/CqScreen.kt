/*
 *abiola 2022
 */

package com.mshdabiola.composequestion

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.MyTextField
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.ui.QuestionDialog
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.image.ContentView
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun CqRoute(
    modifier: Modifier = Modifier,
    examId: Long,
    questionId: Long,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: () -> Unit,
    navigateToTopic: (Long, Long) -> Unit,
    navigateToInstruction: (Long, Long) -> Unit,
) {
    val viewModel: CqViewModel = koinViewModel(parameters = { parametersOf(examId, questionId) })


    var itemUiState by remember { mutableStateOf<ItemUiState?>(null) }

    val update = viewModel.cqState.collectAsStateWithLifecycleCommon()
    LaunchedEffect(update.value) {
        if (update.value is CqState.Loading && (update.value as CqState.Loading).isLoading) {
            onFinish()

            onShowSnack("Add Question", null)
        }
    }
    CqScreen(
        modifier = modifier,
        cqState = update.value,
        addUp = viewModel::addUP,
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
        changeView = viewModel::changeView,
        navigateToTopic = { navigateToTopic(viewModel.subjectId, -1) },
        navigateToInstruction = { navigateToInstruction(examId, -1) },
        onTopicChange = viewModel::onTopicChange,
        onInstructionChange = viewModel::onInstructionChange,
        onItemClicked = { itemUiState = it },
    )
    QuestionDialog(
        itemUiState = itemUiState,
        onDismiss = { itemUiState = null },
        examId = examId,
    )
}

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
internal fun CqScreen(
    modifier: Modifier = Modifier,
    cqState: CqState,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    changeView: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onAddQuestion: () -> Unit = {},
    onAddOption: () -> Unit = {},
    onAddAnswer: (Boolean) -> Unit = {},
    isTheory: (Boolean) -> Unit = {},
    navigateToTopic: () -> Unit = {},
    navigateToInstruction: () -> Unit = {},
    onTopicChange: (Int) -> Unit = {},
    onInstructionChange: (Int) -> Unit = {},
    onItemClicked: (ItemUiState) -> Unit = {},

) {


//    var fillIt =
//        rememberUpdatedState(screenSize != ScreenSize.EXPANDED)
    AnimatedContent(targetState = cqState,
            modifier = modifier
            .verticalScroll(state = rememberScrollState())
        .testTag("cq:screen"),
    ){
        when(it){
            is CqState.Success -> MainContent(
                modifier = modifier,
                cqState = it,
                addUp = addUp,
                addBottom = addBottom,
                delete = delete,
                moveUp = moveUp,
                moveDown = moveDown,
                changeView = changeView,
                changeType = changeType,
                onAddQuestion = onAddQuestion,
                onAddOption = onAddOption,
                onAddAnswer = onAddAnswer,
                isTheory = isTheory,
                navigateToTopic = navigateToTopic,
                navigateToInstruction = navigateToInstruction,
                onTopicChange = onTopicChange,
                onInstructionChange = onInstructionChange,
                onItemClicked = onItemClicked,
            )
            is CqState.Loading-> Waiting(modifier)
            else -> {}
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    cqState: CqState.Success,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    changeView: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onAddQuestion: () -> Unit = {},
    onAddOption: () -> Unit = {},
    onAddAnswer: (Boolean) -> Unit = {},
    isTheory: (Boolean) -> Unit = {},
    navigateToTopic: () -> Unit = {},
    navigateToInstruction: () -> Unit = {},
    onTopicChange: (Int) -> Unit = {},
    onInstructionChange: (Int) -> Unit = {},
    onItemClicked: (ItemUiState) -> Unit = {},
) {
    var showConvert by remember { mutableStateOf(false) }
    val state = rememberTextFieldState()

    var topicExpanded by remember { mutableStateOf(false) }
    var instrucExpanded by remember { mutableStateOf(false) }

    val topicState = rememberTextFieldState()
    val instructState = rememberTextFieldState()

    LaunchedEffect(cqState.questionUiState.topicUiState) {
        topicState.clearText()
        if (cqState.questionUiState.topicUiState != null) {
            topicState.edit {
                append("Id ")
                append(cqState.questionUiState.topicUiState?.id.toString())
            }
        }
    }

    LaunchedEffect(cqState.questionUiState.instructionUiState) {
        instructState.clearText()
        if (cqState.questionUiState.instructionUiState != null) {
            instructState.edit {
                append("Id ")
                append(cqState.questionUiState.instructionUiState?.id.toString())
            }
        }
    }

    Column(modifier){
        Section(title = "Question Section")
        Row(Modifier.fillMaxWidth()) {
            ExposedDropdownMenuBox(
                modifier = Modifier.weight(0.5f),
                expanded = topicExpanded,
                onExpandedChange = { topicExpanded = it },
            ) {
                MyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryEditable)
                        .testTag("ci:topic")
                    ,
                    state = topicState,
                    label = { Text("Topic") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = topicExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )

                ExposedDropdownMenu(
                    expanded = topicExpanded,
                    onDismissRequest = { topicExpanded = false },
                ) {
                    if (cqState.topics.isNotEmpty()) {
                        DropdownMenuItem(
                            modifier = Modifier,
                            text = { Text("None") },
                            leadingIcon = {
                                Icon(Icons.Default.Cancel, "cancel")
                            },
                            onClick = {
                                topicExpanded = false
                                onTopicChange(-1)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }

                    cqState.topics.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            modifier = Modifier.testTag("dropdown:item$index"),
                            text = { Text(s.name) },
                            onClick = {
                                onTopicChange(index)
                                topicExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                    DropdownMenuItem(
                        modifier = Modifier,
                        text = { Text("Add Topic") },
                        leadingIcon = {
                            Icon(Icons.Default.Add, "add")
                        },
                        onClick = {
                            topicExpanded = false
                            navigateToTopic()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

            ExposedDropdownMenuBox(
                modifier = Modifier.weight(0.5f),
                expanded = instrucExpanded,
                onExpandedChange = { instrucExpanded = it },
            ) {
                MyTextField(
                    modifier = Modifier.fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryEditable)
                        .testTag("ci:instruction"),
                    state = instructState,
                    label = { Text("Instruction") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = instrucExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )

                ExposedDropdownMenu(
                    expanded = instrucExpanded,
                    onDismissRequest = { instrucExpanded = false },
                ) {
                    if (cqState.instructs.isNotEmpty()) {
                        DropdownMenuItem(
                            modifier = Modifier,
                            text = { Text("None") },
                            leadingIcon = {
                                Icon(Icons.Default.Cancel, "cancel")
                            },
                            onClick = {
                                instrucExpanded = false
                                onInstructionChange(-1)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                    cqState.instructs.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            modifier = Modifier.testTag("dropdown:item$index"),
                            text = { Text("id ${s.id}") },
                            onClick = {
                                onInstructionChange(index)

                                instrucExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                    DropdownMenuItem(
                        modifier = Modifier,
                        text = { Text("Add Instruct") },
                        leadingIcon = {
                            Icon(Icons.Default.Add, "add")
                        },
                        onClick = {
                            instrucExpanded = false
                            navigateToInstruction()
                        },
                    )
                }
            }
        }
        if (cqState.questionUiState.topicUiState != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Topic:", color = MaterialTheme.colorScheme.secondary)
                Text(" ${cqState.questionUiState.topicUiState!!.name}")
            }
        }
        if (cqState.questionUiState.instructionUiState != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Instruction",
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = cqState.questionUiState.instructionUiState!!.title.text.toString(),
            )
            ContentView(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = cqState.questionUiState.instructionUiState!!.content,
                examId = 4,
                color = Color.Transparent,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Content(
            modifier = Modifier.testTag("cq:content"),
            items = cqState.questionUiState.contents,
            label = "Question",
            addUp = { addUp(-1, it) },
            addBottom = { addBottom(-1, it) },
            delete = { delete(-1, it) },
            moveUp = { moveUp(-1, it) },
            moveDown = { moveDown(-1, it) },
            changeView = { changeView(-1, it) },
            changeType = { i, t -> changeType(-1, i, t) },
            onItemClicked = onItemClicked,
            // onTextChange = { i, s -> onTextChange(-1, i, s) },

        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 2,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            //  horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.CenterHorizontally)
        ) {
            cqState.questionUiState.options?.forEachIndexed { i, optionUiState ->

//                Box(modifier=Modifier.height(20.dp).fillMaxWidth(0.49f).background(if(i%2==0)Color.Blue else Color.Black))

                Content(
                    modifier = Modifier.fillMaxWidth(if (cqState.fillIt) 1f else 0.499999f), // .weight(0.5f)
                    items = optionUiState.content,
                    label = "Option ${i + 1}",
                    addUp = { addUp(i, it) },
                    addBottom = { addBottom(i, it) },
                    delete = { delete(i, it) },
                    moveUp = { moveUp(i, it) },
                    moveDown = { moveDown(i, it) },
                    changeView = { changeView(i, it) },
                    changeType = { ii, t -> changeType(i, ii, t) },
                    onItemClicked = onItemClicked,
                    // onTextChange = { idn, s -> onTextChange(i, idn, s) },

                )
            }
        }

        if (cqState.questionUiState.answers != null && cqState.questionUiState.answers!!.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Text("Answer", modifier = Modifier.padding(horizontal = 16.dp))
            Content(
                items = cqState.questionUiState.answers!!,
                label = "Answer",
                addUp = { addUp(-2, it) },
                addBottom = { addBottom(-2, it) },
                delete = { delete(-2, it) },
                moveUp = { moveUp(-2, it) },
                moveDown = { moveDown(-2, it) },
                changeView = { changeView(-2, it) },
                changeType = { i, t -> changeType(-2, i, t) },
                // onTextChange = { i, s -> onTextChange(-2, i, s) },

            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Is Theory")
                Spacer(Modifier.width(4.dp))
                Switch(
                    checked = cqState.questionUiState.isTheory,
                    onCheckedChange = { isTheory(it) },
                    enabled = cqState.questionUiState.id < 0,
                )
            }

            SeriesEditorButton(
                modifier = Modifier.testTag("cq:add_question"),
                onClick = onAddQuestion,
            ) {
                Icon(Icons.Default.Add, "add")
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("Add Question")
            }
        }
        FlowRow(
            Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SuggestionChip(
                onClick = onAddOption,
                label = { Text("Add Option") },
            )

            // TODO("fix answer not null")
            SuggestionChip(
                modifier = Modifier.testTag("cq:add_answer"),
                onClick = { onAddAnswer(cqState.questionUiState.answers == null) },
                label = {
                    Text(
                        if (cqState.questionUiState.answers == null) "Add Answers" else "Remove answer",
                    )
                },
            )
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
            Text("Convert text to Questions")
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
                label = "Exam Input",
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
        // TemplateUi()
    }

}
