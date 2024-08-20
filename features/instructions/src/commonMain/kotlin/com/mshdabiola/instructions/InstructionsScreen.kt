/*
 *abiola 2022
 */

package com.mshdabiola.instructions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.image.ContentView
import com.mshdabiola.ui.state.InstructionUiState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import serieseditor.features.instructions.generated.resources.Res
import serieseditor.features.instructions.generated.resources.features_main_empty_description
import serieseditor.features.instructions.generated.resources.features_main_empty_error
import serieseditor.features.instructions.generated.resources.features_main_loading

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun InstructionsRoute(
    modifier: Modifier = Modifier,
    navigateToComposeInstruction: (Long) -> Unit,
    examId: Long,
) {
    val viewModel: InstructionsViewModel = koinViewModel(parameters = { parametersOf(examId) })

    val feedNote = viewModel.instructions.collectAsStateWithLifecycleCommon()

    InstructionScreen(
        modifier = modifier,
        mainState = feedNote.value,
        onUpdate = {
            navigateToComposeInstruction(it)
        },
        onDelete = viewModel::onDelete,

    )
}

@Composable
internal fun InstructionScreen(
    modifier: Modifier = Modifier,
    mainState: Result<List<InstructionUiState>>,
    onUpdate: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
) {
    val state = rememberLazyListState()

    Box(
        modifier = modifier
            .testTag("instruction:screen"),

    ) {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .testTag("instruction:list"),
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
                        instructionItems(
                            items = mainState.data,
                            onUpdate = onUpdate,
                            onDelete = onDelete,
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = instructionsItemsSize(mainState)
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
            .testTag("main:loading"),
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

private fun instructionsItemsSize(
    topicUiState: Result<List<InstructionUiState>>,
) = when (topicUiState) {
    is Result.Error -> 0 // Nothing
    is Result.Loading -> 1 // Loading bar
    is Result.Success -> topicUiState.data.size + 2
}

fun LazyListScope.instructionItems(
    items: List<InstructionUiState>,
    onUpdate: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
) = items(
    items = items,
    key = { it.id },
    itemContent = { instructionUiState ->
        val analyticsHelper = LocalAnalyticsHelper.current

        InstructionUi(
            modifier = Modifier,
            instructionUiState = instructionUiState,
            onUpdate = onUpdate,
            onDelete = onDelete,
        )
    },
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InstructionUi(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    onUpdate: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
) {
    var showDrop by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(text = instructionUiState.title.text.toString())
            }
            Box {
                IconButton(onClick = { showDrop = true }) {
                    Icon(Icons.Default.MoreVert, "more")
                }
                DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Update, "update") },
                        text = { Text("Update") },
                        onClick = {
                            onUpdate(instructionUiState.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(instructionUiState.id)
                            showDrop = false
                        },
                    )
                }
            }
        }
        ContentView(
            items = instructionUiState.content,
            examId = instructionUiState.examId,
            color = Color.Transparent,
        )
    }
}
