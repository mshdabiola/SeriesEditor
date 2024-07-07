package com.mshdabiola.serieseditor.ui.exampanelother

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composeinstruction.navigation.navigateToComposeInstruction
import com.mshdabiola.composequestion.navigation.navigateToComposeQuestion
import com.mshdabiola.instructions.navigation.INSTRUCTION_ROUTE
import com.mshdabiola.instructions.navigation.instructionScreen
import com.mshdabiola.questions.navigation.QUESTIONS_ROUTE
import com.mshdabiola.questions.navigation.questionScreen
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.SeriesEditorAppState
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExamPaneScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    appState: Other,
    examId: Long,
) {
    var state by remember {
        mutableStateOf(0)
    }


    val coroutineScope = rememberCoroutineScope()
    val questionNavHostController = rememberNavController()
    val instructionNavHostController = rememberNavController()


    Column(modifier) {
        TabRow(
            selectedTabIndex = appState.pagerState.currentPage,
            modifier = Modifier,

            ) {
            Tab(
                selected = state == 0,
                onClick = {
                    coroutineScope.launch {
                        appState.pagerState.animateScrollToPage(0)

                    }
                },
                text = { Text("Question") },
            )
            Tab(
                selected = state == 1,
                onClick = {
                    coroutineScope.launch {
                        appState.pagerState.animateScrollToPage(1)

                    }
                },
                text = { Text("Instruction") },
            )

        }
        HorizontalPager(state = appState.pagerState) {
            when (it) {
                0 -> {
                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier,
                            startDestination = QUESTIONS_ROUTE,
                            navController = questionNavHostController,
                        ) {
                            questionScreen(
                                modifier = Modifier.fillMaxSize(),
                                onShowSnack = onShowSnackbar,
                                defaultExamId = examId,
                                navigateToComposeQuestion = appState.navController::navigateToComposeQuestion,
                            )
                        }
                    }
                }

                1 -> {
                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier,
                            startDestination = INSTRUCTION_ROUTE,
                            navController = instructionNavHostController,
                        ) {
                            instructionScreen(
                                modifier = Modifier.fillMaxSize(),
                                onShowSnack = onShowSnackbar,
                                navigateToComposeInstruction = appState.navController::navigateToComposeInstruction,
                                defaultExamId = examId,

                                )
                        }
                    }

                }

                else -> {}
            }
        }


    }


}