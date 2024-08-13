/*
 *abiola 2022
 */

package com.mshdabiola.subjects

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.component.SeriesEditorLoadingWheel
import com.mshdabiola.designsystem.component.scrollbar.DraggableScrollbar
import com.mshdabiola.designsystem.component.scrollbar.rememberDraggableScroller
import com.mshdabiola.designsystem.component.scrollbar.scrollbarState
import com.mshdabiola.designsystem.drawable.emptyCartIcon
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.seriesmodel.SubjectWithSeries
import com.mshdabiola.ui.logNoteOpened
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import serieseditor.features.subjects.generated.resources.Res
import serieseditor.features.subjects.generated.resources.features_main_empty_description
import serieseditor.features.subjects.generated.resources.features_main_empty_error
import serieseditor.features.subjects.generated.resources.features_main_loading

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun SubjectRoute(
    modifier: Modifier = Modifier,
    navigateToQuestion: (Long) -> Unit,
    updateSubjects: (Long) -> Unit,
) {
    val viewModel: SubjectViewModel =
        koinViewModel()

    val feedNote = viewModel.subjects.collectAsStateWithLifecycle()

    SubjectScreen(
        modifier = modifier,
        mainState = feedNote.value,
        navigateToQuestion = navigateToQuestion,
        onDelete = viewModel::onDelete,
        onUpdate = updateSubjects,

    )
}

@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    mainState: Result<List<SubjectWithSeries>>,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    navigateToQuestion: (Long) -> Unit = {},
) {
    val state = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("subjects:screen"),

    ) {
        LazyColumn(
            state = state,
            // contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .testTag("subjects:list"),
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
                        subjectItems(
                            items = mainState.data,
                            onClick = navigateToQuestion,
                            itemModifier = Modifier,
                            onDelete = onDelete,
                            onUpdate = onUpdate,
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = subjectsItemsSize(mainState)
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
            .testTag("subjects:loading"),
        contentDesc = stringResource(Res.string.features_main_loading),
    )
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("subjects:empty"),
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

private fun subjectsItemsSize(
    subjects: Result<List<SubjectWithSeries>>,
) = when (subjects) {
    is Result.Error -> 0 // Nothing
    is Result.Loading -> 1 // Loading bar
    is Result.Success -> subjects.data.size + 2
}

fun LazyListScope.subjectItems(
    items: List<SubjectWithSeries>,
    onClick: (Long) -> Unit,
    itemModifier: Modifier = Modifier,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
) = items(
    items = items,
    key = { it.subject.id },
    itemContent = { subject ->
        val analyticsHelper = LocalAnalyticsHelper.current

        SubjectCard(
            modifier = itemModifier,
            subjectWithSeries = subject,
            onDelete = onDelete,
            onUpdate = onUpdate,
            onClick = {
                analyticsHelper.logNoteOpened(it.toString())
                onClick(it)
            },
        )
    },
)
