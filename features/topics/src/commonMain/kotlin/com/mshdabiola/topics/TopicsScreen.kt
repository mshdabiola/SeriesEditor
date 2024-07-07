/*
 *abiola 2022
 */

package com.mshdabiola.topics

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.component.SeriesEditorLoadingWheel
import com.mshdabiola.designsystem.component.scrollbar.DraggableScrollbar
import com.mshdabiola.designsystem.component.scrollbar.rememberDraggableScroller
import com.mshdabiola.designsystem.component.scrollbar.scrollbarState
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.state.TopicUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import serieseditor.features.topics.generated.resources.Res
import serieseditor.features.topics.generated.resources.features_main_empty_description
import serieseditor.features.topics.generated.resources.features_main_empty_error
import serieseditor.features.topics.generated.resources.features_main_img_empty_bookmarks
import serieseditor.features.topics.generated.resources.features_main_loading

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun TopicsRoute(
    modifier: Modifier = Modifier,
    navigateToComposeTopic: (Long) -> Unit,
    subjectId: Long,
) {
    val viewModel: TopicsViewModel = koinViewModel(parameters = { parametersOf(subjectId) })

    val feedNote = viewModel.topics.collectAsStateWithLifecycleCommon()

    TopicScreen(
        modifier = modifier,
        mainState = feedNote.value,
        onUpdate = {
            navigateToComposeTopic(it)
        },
        onDelete = viewModel::onDelete,

    )
}

@Composable
internal fun TopicScreen(
    modifier: Modifier = Modifier,
    mainState: Result<List<TopicUiState>>,
    onUpdate: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
) {
    val state = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("main:screen"),

        ) {
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .testTag("main:list"),
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
                        topicItems(
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
        val itemsAvailable = topicsItemsSize(mainState)
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
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(Res.drawable.features_main_img_empty_bookmarks),
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

private fun topicsItemsSize(
    topicUiState: Result<List<TopicUiState>>,
) = when (topicUiState) {
    is Result.Error -> 0 // Nothing
    is Result.Loading -> 1 // Loading bar
    is Result.Success -> topicUiState.data.size + 2
}


fun LazyListScope.topicItems(
    items: List<TopicUiState>,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
) = items(
    items = items,
    key = { it.id },
    itemContent = { topicUiState ->
        val analyticsHelper = LocalAnalyticsHelper.current

      TopicCard(
          modifier = Modifier,
          topicUiState = topicUiState,
          onDelete = onDelete,
          onUpdate = onUpdate

      )
    },
)


