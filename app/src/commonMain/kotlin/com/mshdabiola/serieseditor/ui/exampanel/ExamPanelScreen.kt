package com.mshdabiola.serieseditor.ui.exampanel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExamPaneScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    navigateToTopicPanel: (Long) -> Unit = { },
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
                                    navigateToInstruction = { _, _ ->
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    },
                                    navigateToTopic = { id ->
                                        navigateToTopicPanel(id)
                                    },

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
