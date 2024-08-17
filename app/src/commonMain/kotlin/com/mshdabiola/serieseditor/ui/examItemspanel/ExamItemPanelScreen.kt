package com.mshdabiola.serieseditor.ui.examItemspanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
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
import com.mshdabiola.serieseditor.ui.Other
import kotlinx.coroutines.launch


@Composable
fun ExamItemPaneOtherScreen(
    modifier: Modifier = Modifier,
    appState: Other,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    examId: Long,
) {

    val screenModifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)

    val coroutineScope = rememberCoroutineScope()
    val questionNavHostController = rememberNavController()
    val instructionNavHostController = rememberNavController()

    Column(modifier) {
        Box(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.primaryContainer,
            ).fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars),
        ) {
            TabRow(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                selectedTabIndex = appState.examPagerState.currentPage,
                modifier = Modifier.statusBarsPadding(),

                ) {
                Tab(
                    selected = appState.examPagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            appState.examPagerState.animateScrollToPage(0)
                        }
                    },
                    text = { Text("Question") },
                )
                Tab(
                    selected = appState.examPagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            appState.examPagerState.animateScrollToPage(1)
                        }
                    },
                    text = { Text("Instruction") },
                )
            }
        }

        HorizontalPager(state = appState.examPagerState) {
            when (it) {
                0 -> {
                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier,
                            startDestination = QUESTIONS_ROUTE,
                            navController = questionNavHostController,
                        ) {
                            questionScreen(
                                modifier = screenModifier,
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
                                modifier = screenModifier,
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

@Composable
fun ExamItemPaneScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    examId: Long,
) {
    var state by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState(pageCount = { 2 })
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
                selected = state == 0,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                text = { Text("Question") },
            )
            Tab(
                selected = state == 1,
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
                                navigateToComposeQuestion = cmNavHostController::navigateToComposeQuestion,
                            )
                        }
                        Column(Modifier.weight(0.4f)) {
                            NavHost(
                                navController = cmNavHostController,
                                startDestination = COMPOSE_QUESTION_ROUTE,
                                modifier = Modifier,

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
                                navigateToComposeInstruction = ciNavHostController::navigateToComposeInstruction,
                                defaultExamId = examId,

                                )
                        }
                        Column(Modifier.weight(0.4f)) {
                            NavHost(
                                navController = ciNavHostController,
                                startDestination = COMPOSE_INSTRUCTION_ROUTE,
                                modifier = Modifier,
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
