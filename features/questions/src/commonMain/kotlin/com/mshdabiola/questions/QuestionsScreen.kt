/*
 *abiola 2022
 */

package com.mshdabiola.questions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.component.SeriesEditorLoadingWheel
import com.mshdabiola.designsystem.component.scrollbar.DraggableScrollbar
import com.mshdabiola.designsystem.component.scrollbar.rememberDraggableScroller
import com.mshdabiola.designsystem.component.scrollbar.scrollbarState
import com.mshdabiola.designsystem.drawable.emptyCartIcon
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.designsystem.theme.extendedColorScheme
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.image.ContentView
import com.mshdabiola.ui.state.QuestionUiState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import serieseditor.features.questions.generated.resources.Res
import serieseditor.features.questions.generated.resources.features_main_empty_description
import serieseditor.features.questions.generated.resources.features_main_empty_error
import serieseditor.features.questions.generated.resources.features_main_loading

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun QuestionsRoute(
    modifier: Modifier = Modifier,
    navigateToComposeQuestion: (Long) -> Unit,
    examId: Long,
) {
    val viewModel: QuestionsViewModel = koinViewModel(parameters = { parametersOf(examId) })

    val feedNote = viewModel.questions.collectAsStateWithLifecycleCommon()

    QuestionsScreen(
        modifier = modifier,
        mainState = feedNote.value,
        onUpdate = {
            navigateToComposeQuestion(it)
        },
        onMoveUp = viewModel::onMoveUpQuestion,
        onMoveDown = viewModel::onMoveDownQuestion,
        onDelete = viewModel::onDeleteQuestion,
        onAnswer = viewModel::onAnswerClick,

    )
}

@Composable
internal fun QuestionsScreen(
    modifier: Modifier = Modifier,
    mainState: Result<List<QuestionUiState>>,
    onUpdate: (Long) -> Unit = {},
    onMoveUp: (Long) -> Unit = {},
    onMoveDown: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
) {
    val state = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("question:screen"),

    ) {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .testTag("question:list"),
        ) {
            item {
                // Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }

            when (mainState) {
                is Result.Loading -> item {
                    LoadingState()
                }

                is Result.Error -> TODO()
                is Result.Success -> {
                    if (mainState.data.isEmpty()) {
                        item {
                            EmptyState()
                        }
                    } else {
                        questionItems(
                            items = mainState.data,
                            onUpdate = onUpdate,
                            onMoveUp = onMoveUp,
                            onMoveDown = onMoveDown,
                            onDelete = onDelete,
                            onAnswer = onAnswer,
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = questionsItemsSize(mainState)
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable,
        )
        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    SeriesEditorLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .testTag("question:loading"),
        contentDesc = stringResource(Res.string.features_main_loading),
    )
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("main:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconTint = LocalTintTheme.current.iconTint
        Image(
            modifier = Modifier.size(200.dp),
            painter = emptyCartIcon,
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_description),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

private fun questionsItemsSize(
    topicUiState: Result<List<QuestionUiState>>,
) = when (topicUiState) {
    is Result.Error -> 0 // Nothing
    is Result.Loading -> 1 // Loading bar
    is Result.Success -> topicUiState.data.size + 2
}

fun LazyListScope.questionItems(
    items: List<QuestionUiState>,
    onUpdate: (Long) -> Unit = {},
    onMoveUp: (Long) -> Unit = {},
    onMoveDown: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
) = items(
    items = items,
    key = { it.id },
    itemContent = { examUiState ->
        val analyticsHelper = LocalAnalyticsHelper.current

        QuestionUi(
            questionUiState = examUiState,
            onUpdate = onUpdate,
            onMoveUp = onMoveUp,
            onMoveDown = onMoveDown,
            onDelete = onDelete,
            onAnswer = onAnswer,
        )
    },
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    onUpdate: (Long) -> Unit = {},
    onMoveUp: (Long) -> Unit = {},
    onMoveDown: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
) {
    var showDrop by remember {
        mutableStateOf(false)
    }

    Column(modifier) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (questionUiState.topicUiState != null) {
                Text("Topic:", color = MaterialTheme.colorScheme.secondary)
                Text(" ${questionUiState.topicUiState!!.name}   ")
            }
            Text(
                modifier = Modifier.weight(1f),
                text = if (questionUiState.isTheory) "Theory" else "Objective",
            )
            Box {
                IconButton(onClick = { showDrop = true }) {
                    Icon(Icons.Default.MoreVert, "more")
                }
                DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Update, "update") },
                        text = { Text("Update") },
                        onClick = {
                            onUpdate(questionUiState.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.ArrowUpward, "up") },
                        text = { Text("Move Up") },
                        onClick = {
                            onMoveUp(questionUiState.id)
                            showDrop = false
                        },
                    )
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.ArrowDownward, "down") },
                        text = { Text("Move Down") },
                        onClick = {
                            onMoveDown(questionUiState.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(questionUiState.id)
                            showDrop = false
                        },
                    )
                }
            }
        }
//            Spacer(modifier = Modifier.height(4.dp))

        if (questionUiState.instructionUiState != null) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Instruction",
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = questionUiState.instructionUiState!!.title.text.toString(),
            )
            ContentView(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = questionUiState.instructionUiState!!.content,
                examId = questionUiState.examId,
                color = Color.Transparent,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        ContentView(
            items = questionUiState.contents,
            examId = questionUiState.examId,
            color = Color.Transparent,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            text = "Options",
            color = MaterialTheme.colorScheme.secondary,
        )
        questionUiState.options?.chunked(2)?.forEach { optionsUiStates ->
            Row {
                optionsUiStates.forEach { optionsUiState ->
                    ContentView(
                        modifier = Modifier
                            .weight(0.5f)
                            .clickable(
                                onClick = {
                                    onAnswer(
                                        questionUiState.id,
                                        optionsUiState.id,
                                    )
                                },
                            ),
                        color = if (optionsUiState.isAnswer) {
                            extendedColorScheme.right.colorContainer
                        } else {
                            Color.Transparent
                        },

                        items = optionsUiState.content,
                        examId = questionUiState.examId,
                    )
                }
            }
        }

        if (questionUiState.answers != null && !questionUiState.answers!!.isEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text("Answer", modifier = Modifier.padding(horizontal = 16.dp))
            ContentView(
                items = questionUiState.answers!!,
                examId = questionUiState.examId,
                color = Color.Transparent,

            )
        }
    }
}
