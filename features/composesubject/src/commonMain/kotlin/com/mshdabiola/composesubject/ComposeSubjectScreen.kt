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
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NavigateBefore
import androidx.compose.material.icons.automirrored.outlined.NavigateNext
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
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
import com.mshdabiola.data.model.Update
import com.mshdabiola.designsystem.component.Section
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Series
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

    val update = viewModel.update.collectAsStateWithLifecycleCommon()
    val series = viewModel.series.collectAsStateWithLifecycleCommon()


    LaunchedEffect(update.value) {
        if (update.value == Update.Success) {
            onFinish()
            onShowSnack("Add Subject", null)
        }
    }
    SubjectScreen(
        modifier = modifier,
        subjectState = viewModel.subjectState,
        seriesState = viewModel.seriesState,
        currentSeries = viewModel.currentSeriesId.value,
        series = series.value,
        update = update.value,
        addSubject = viewModel::addSubject,
        addSeries = viewModel::addSeries,
        onSeriesChange = viewModel::onCurrentSeriesChange,
        onDeleteSeries = viewModel::onDeleteCurrentSeries,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun SubjectScreen(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    seriesState: TextFieldState,
    currentSeries: Long,
    series: List<Series>,
    update: Update,
    addSubject: () -> Unit = {},
    addSeries: () -> Unit = {},
    onSeriesChange: (Long) -> Unit = {},
    onDeleteSeries: () -> Unit = {},
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .testTag("composesubject:screen"),

        ) {
        when (update) {
            Update.Edit -> {
                Section(title = "Subject Section")
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
                        series.forEach {
                            FilterChip(
                                leadingIcon = {
                                    if (it.id == currentSeries) {
                                        Icon(
                                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                                            imageVector = Icons.Default.Done,
                                            contentDescription = "done",
                                        )
                                    }
                                },
                                selected = currentSeries == it.id,
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
                        .testTag("composesubject:subject"),
                    state = subjectState,
                    label = "Subject",
                    placeholder = "Mathematics",
                    keyboardAction = { addSubject() },
                    maxNum = TextFieldLineLimits.SingleLine,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SeriesEditorButton(
                    modifier = Modifier.align(Alignment.End),
                    enabled = subjectState.text.isNotBlank() && currentSeries > 0,
                    onClick = addSubject,
                ) {
                    Icon(Icons.Default.Add, "Add")
                    Text("Add Subject")
                }

                Section(title = "Type Section")

                SeriesEditorTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("composesubject:series"),
                    state = seriesState,
                    label = "Type",
                    placeholder = "NECO",
                    keyboardAction = { addSeries() },
                    maxNum = TextFieldLineLimits.SingleLine,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                )
                {
                    AnimatedVisibility(currentSeries > 1) {
                        TextButton(onClick = onDeleteSeries) {
                            Text("Delete")
                        }
                    }

                    SeriesEditorButton(
                        modifier = Modifier,
                        enabled = seriesState.text.isNotBlank(),
                        onClick = addSeries,
                    ) {
                        AnimatedContent(currentSeries) {
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

            Update.Saving -> {
                Waiting()
            }

            else -> {}
        }
    }
}
