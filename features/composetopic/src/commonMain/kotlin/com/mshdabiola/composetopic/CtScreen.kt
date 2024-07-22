/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateBefore
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    val categories = viewModel.categories.collectAsStateWithLifecycleCommon()
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
        categoryState = viewModel.categoryState,
        currentCategory = viewModel.currentCategoryId.value,
        categories = categories.value,
        name = viewModel.state,
        topicInput = viewModel.topicInput,
        onAddTopic = viewModel::addTopic,
        onAddTopicInput = viewModel::addTopicInput,
        onCategoryChange = viewModel::onCurrentSeriesChange,
        addCategory = viewModel::addCategory,
        onDeleteCategory = viewModel::onDeleteCategory,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun CtScreen(
    modifier: Modifier = Modifier,
    topicId: Long,
    name: TextFieldState,
    categoryState: TextFieldState,
    currentCategory: Long,
    categories: List<TopicCategory>,
    topicInput: TextFieldState,
    onAddTopic: () -> Unit = {},
    onAddTopicInput: () -> Unit = {},
    update: Update,
    addCategory: () -> Unit = {},
    onCategoryChange: (Long) -> Unit = {},
    onDeleteCategory: () -> Unit = {},
) {
    var showConvert by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.imePadding()
            .verticalScroll(rememberScrollState()),
    ) {
        when (update) {
            Update.Edit -> {
                Section(title = "Topic Section")
                val focusRequester = remember {
                    FocusRequester()
                }
                LaunchedEffect(Unit) {
                    delay(1000)
                    focusRequester.requestFocus()
                }

                Row(Modifier.fillMaxWidth()) {
                    TooltipBox(
                        tooltip = { Text("Scroll to the next") },
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        state = rememberTooltipState(),
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value - 40)
                                }
                            },
                            enabled = scrollState.canScrollBackward,
                        ) {
                            Icon(Icons.AutoMirrored.Outlined.NavigateBefore, "previous")
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f).horizontalScroll(scrollState),
                    ) {
                        categories.forEach {
                            FilterChip(
                                leadingIcon = {
                                    if (it.id == currentCategory) {
                                        Icon(
                                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                                            imageVector = Icons.Default.Done,
                                            contentDescription = "done",
                                        )
                                    }
                                },
                                selected = currentCategory == it.id,
                                onClick = {
                                    onCategoryChange(it.id)
                                },
                                label = { Text(it.name) },
                            )
                        }
                    }

                    TooltipBox(
                        tooltip = { Text("Scroll to the next") },
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        state = rememberTooltipState(),
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value + 40)
                                }
                            },
                            enabled = scrollState.canScrollForward,
                        ) {
                            Icon(Icons.AutoMirrored.Outlined.NavigateNext, "next")
                        }
                    }
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
                    modifier = Modifier.align(Alignment.End),
                    enabled = name.text.isNotBlank(),
                    onClick = onAddTopic,
                ) {
                    Text(if (topicId < 0) "Add topic" else "Update topic")
                }

                Section(title = "Type Section")

                SeriesEditorTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("composesubject:category"),
                    state = categoryState,
                    label = "Category",
                    placeholder = "Algebra",
                    keyboardAction = { addCategory() },
                    maxNum = TextFieldLineLimits.SingleLine,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    AnimatedVisibility(currentCategory > 1) {
                        TextButton(onClick = onDeleteCategory) {
                            Text("Delete")
                        }
                    }

                    SeriesEditorButton(
                        modifier = Modifier,
                        enabled = categoryState.text.isNotBlank(),
                        onClick = addCategory,
                    ) {
                        AnimatedContent(currentCategory) {
                            if (it > 1) {
                                Row {
                                    Icon(Icons.Default.Update, "update")
                                    Text("Update Category")
                                }
                            } else {
                                Row {
                                    Icon(Icons.Default.Add, "Add")
                                    Text("Add Category")
                                }
                            }
                        }
                    }
                }
            }

            Update.Saving -> {
                Waiting()
            }

            else -> {
            }
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
