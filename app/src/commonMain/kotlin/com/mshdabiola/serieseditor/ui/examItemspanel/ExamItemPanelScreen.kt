package com.mshdabiola.serieseditor.ui.examItemspanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composeinstruction.navigation.COMPOSE_INSTRUCTION_ROUTE
import com.mshdabiola.composeinstruction.navigation.composeInstructionScreen
import com.mshdabiola.composeinstruction.navigation.navigateToComposeInstruction
import com.mshdabiola.composequestion.navigation.COMPOSE_QUESTION_ROUTE
import com.mshdabiola.composequestion.navigation.composeQuestionScreen
import com.mshdabiola.composequestion.navigation.navigateToComposeQuestion
import com.mshdabiola.instructions.navigation.INSTRUCTION_ROUTE
import com.mshdabiola.instructions.navigation.instructionScreen
import com.mshdabiola.questions.navigation.QUESTIONS_ROUTE
import com.mshdabiola.questions.navigation.questionScreen
import com.mshdabiola.serieseditor.ui.SeriesEditorAppState
import kotlinx.coroutines.launch

@Composable
fun ExamItemPaneScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    examId: Long,
    appState: SeriesEditorAppState,
) {
    val pagerState = appState.examPagerState
    val coroutineScope = rememberCoroutineScope()
    val questionNavHostController = rememberNavController()
    val cmNavHostController = rememberNavController()
    val instructionNavHostController = rememberNavController()
    val ciNavHostController = rememberNavController()
    val screenModifier = modifier.fillMaxSize().padding(8.dp)

    Column(modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier,

        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                text = { Text("Question") },
            )
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                text = { Text("Instruction") },
            )
        }
        HorizontalPager(state = pagerState) {
            when (it) {
                0 -> {
                    Row(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier.weight(0.6f),
                            startDestination = QUESTIONS_ROUTE,
                            navController = questionNavHostController,
                        ) {
                            questionScreen(
                                modifier = screenModifier,
                                onShowSnack = onShowSnackbar,
                                defaultExamId = examId,
                                navigateToComposeQuestion =
                                { id1, id2 ->
                                    if (isSmallScreen) {
                                        appState.navController.navigateToComposeQuestion(
                                            id1,
                                            id2,
                                        )
                                    } else {
                                        cmNavHostController.navigateToComposeQuestion(id1, id2)
                                    }
                                },
                            )
                        }
                        if (appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                            NavHost(
                                navController = cmNavHostController,
                                startDestination = COMPOSE_QUESTION_ROUTE,
                                modifier = Modifier.weight(0.4f),
                            ) {
                                composeQuestionScreen(
                                    modifier = screenModifier,
                                    onShowSnack = onShowSnackbar,
                                    onFinish = {
                                        cmNavHostController.popBackStack()
                                        if (cmNavHostController.currentDestination == null) {
                                            cmNavHostController.navigateToComposeQuestion(
                                                examId,
                                                -1,
                                            )
                                        }
                                    },
                                    defaultExamId = examId,

                                )
                            }
                        }
                    }
                }

                1 -> {
                    Row(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier.weight(0.6f),
                            startDestination = INSTRUCTION_ROUTE,
                            navController = instructionNavHostController,
                        ) {
                            instructionScreen(
                                modifier = screenModifier,
                                onShowSnack = onShowSnackbar,
                                navigateToComposeInstruction =
                                { id1, id2 ->
                                    if (isSmallScreen) {
                                        appState.navController.navigateToComposeInstruction(
                                            id1,
                                            id2,
                                        )
                                    } else {
                                        ciNavHostController.navigateToComposeInstruction(
                                            id1,
                                            id2,
                                        )
                                    }
                                },

                                defaultExamId = examId,

                            )
                        }
                        if (appState.windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                            NavHost(
                                navController = ciNavHostController,
                                startDestination = COMPOSE_INSTRUCTION_ROUTE,
                                modifier = Modifier.weight(0.4f),
                            ) {
                                composeInstructionScreen(
                                    modifier = screenModifier,
                                    onShowSnack = onShowSnackbar,
                                    onFinish = {
                                        ciNavHostController.popBackStack()
                                        if (ciNavHostController.currentDestination == null) {
                                            ciNavHostController.navigateToComposeInstruction(
                                                examId,
                                                -1,
                                            )
                                        }
                                    },
                                    defaultExamId = examId,

                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
