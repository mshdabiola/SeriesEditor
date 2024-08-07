/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateBefore
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Update
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.coroutines.launch
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
        seriesState = viewModel.seriesState,
        csState = update.value,
        addSubject = viewModel::addSubject,
        addSeries = viewModel::addSeries,
        onSeriesChange = viewModel::onCurrentSeriesChange,
        onDeleteSeries = viewModel::onDeleteCurrentSeries,
    )
}

@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    seriesState: TextFieldState,
    csState: CsState,
    addSubject: () -> Unit = {},
    addSeries: () -> Unit = {},
    onSeriesChange: (Long) -> Unit = {},
    onDeleteSeries: () -> Unit = {},
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
                seriesState = seriesState,
                csState = it,
                addSubject = addSubject,
                addSeries = addSeries,
                onSeriesChange = onSeriesChange,
                onDeleteSeries = onDeleteSeries,
            )

            is CsState.Loading -> {
                Waiting()
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    seriesState: TextFieldState,
    csState: CsState.Success,
    addSubject: () -> Unit = {},
    addSeries: () -> Unit = {},
    onSeriesChange: (Long) -> Unit = {},
    onDeleteSeries: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Section(title = "Subject Section")
        Row(Modifier.fillMaxWidth()) {
            TooltipBox(
                tooltip = { Text("Scroll to the next") },
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                state = rememberTooltipState(),
            ) {
                IconButton(
                    modifier = Modifier.testTag("cs:previous"),
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
                modifier = Modifier.testTag("cs:list_series").weight(1f).horizontalScroll(scrollState),
            ) {
                csState.series.forEach {
                    FilterChip(
                        leadingIcon = {
                            if (it.id == csState.currentSeries) {
                                Icon(
                                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "done",
                                )
                            }
                        },
                        selected = csState.currentSeries == it.id,
                        onClick = {
                            onSeriesChange(it.id)
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
                    modifier = Modifier.testTag("cs:next"),
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
            enabled = subjectState.text.isNotBlank() && csState.currentSeries > 0,
            onClick = addSubject,
        ) {
            Icon(Icons.Default.Add, "Add")
            Text("Add Subject")
        }

        Section(title = "Class Section")

        SeriesEditorTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("cs:series"),
            state = seriesState,
            label = "Class",
            placeholder = "Freshman",
            keyboardAction = { addSeries() },
            maxNum = TextFieldLineLimits.SingleLine,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        ) {
            AnimatedVisibility(csState.currentSeries > 1) {
                TextButton(
                    modifier = Modifier.testTag("cs:delete_series"),
                    onClick = onDeleteSeries,
                ) {
                    Text("Delete")
                }
            }

            SeriesEditorButton(
                modifier = Modifier.testTag("cs:add_series"),
                enabled = seriesState.text.isNotBlank(),
                onClick = addSeries,
            ) {
                AnimatedContent(csState.currentSeries) {
                    if (it > 1) {
                        Row {
                            Icon(Icons.Default.Update, "update")
                            Text("Update Series")
                        }
                    } else {
                        Row {
                            Icon(Icons.Default.Add, "Add")
                            Text("Add Series")
                        }
                    }
                }
            }
        }
    }
}
