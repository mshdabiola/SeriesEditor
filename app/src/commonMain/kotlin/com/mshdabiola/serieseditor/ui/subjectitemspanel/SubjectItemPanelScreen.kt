package com.mshdabiola.serieseditor.ui.subjectitemspanel

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composeexam.navigation.FULL_COMPOSE_EXAMINATION_ROUTE
import com.mshdabiola.composeexam.navigation.composeExaminationScreen
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.composetopic.navigation.COMPOSE_TOPIC_ROUTE
import com.mshdabiola.composetopic.navigation.composeTopicScreen
import com.mshdabiola.composetopic.navigation.navigateToComposeTopic
import com.mshdabiola.examinations.navigation.DEFAULT_ROUTE
import com.mshdabiola.examinations.navigation.examScreen
import com.mshdabiola.questions.navigation.navigateToQuestion
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.examItemspanel.navigateToExamItemPanel
import com.mshdabiola.topics.navigation.TOPIC_ROUTE
import com.mshdabiola.topics.navigation.topicScreen
import kotlinx.coroutines.launch


@Composable
fun SubjectItemPaneOtherScreen(
    modifier: Modifier = Modifier,
    appState: Other,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    subjectId: Long,
) {

    val screenModifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)

    val coroutineScope = rememberCoroutineScope()
    val examNavHostController = rememberNavController()
    val topicNavHostController = rememberNavController()

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
                selectedTabIndex = appState.subjectPagerState.currentPage,
                modifier = Modifier.statusBarsPadding(),

                ) {
                Tab(
                    selected = appState.subjectPagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            appState.subjectPagerState.animateScrollToPage(0)
                        }
                    },
                    text = { Text("Examinations") },
                )
                Tab(
                    selected = appState.subjectPagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            appState.subjectPagerState.animateScrollToPage(1)
                        }
                    },
                    text = { Text("Topics") },
                )
            }
        }

        HorizontalPager(state = appState.subjectPagerState) {
            when (it) {
                0 -> {
                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier.weight(0.6f),
                            startDestination = DEFAULT_ROUTE,
                            navController = examNavHostController,
                        ) {
                            examScreen(
                                modifier = screenModifier,
                                onShowSnack = onShowSnackbar,
                                navigateToQuestion = appState.navController::navigateToExamItemPanel,
                                updateExam = appState.navController::navigateToComposeExamination,
                                subjectId = subjectId,
                            )
                        }
                    }
                }

                1 -> {
                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = modifier.weight(0.6f),
                            startDestination = TOPIC_ROUTE,
                            navController = topicNavHostController,
                        ) {
                            topicScreen(
                                modifier = screenModifier,
                                onShowSnack = onShowSnackbar,
                                subjectId = subjectId,
                                navigateToComposeTopic = appState.navController::navigateToComposeTopic,
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
fun SubjectItemPaneScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    subjectId: Long,
) {

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

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
                text = { Text("Examinations") },
            )
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                text = { Text("Topics") },
            )
        }
        HorizontalPager(state = pagerState) {
            when (it) {
                0 -> {
                    ExamPanel(
                        modifier = modifier,
                        onShowSnackbar = onShowSnackbar,
                        appState = appState,
                        subjectId = subjectId,
                    )
                }

                1 -> {
                    TopicPanel(
                        modifier = modifier,
                        onShowSnackbar = onShowSnackbar,
                        subjectId = subjectId,
                    )

                }

                else -> {}
            }
        }
    }
}


@Composable
fun ExamPanel(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    appState: Extended,
    subjectId: Long,
) {
    val screenModifier = modifier.fillMaxSize().padding(8.dp)
    val examNavHostController = rememberNavController()
    val ceNavHostController = rememberNavController()

    Row(modifier) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = DEFAULT_ROUTE,
            navController = examNavHostController,
        ) {
            examScreen(
                modifier = screenModifier,
                onShowSnack = onShowSnackbar,
                navigateToQuestion = appState.navController::navigateToExamItemPanel,
                updateExam = ceNavHostController::navigateToComposeExamination,
                subjectId = subjectId,
            )
        }
        Column(Modifier.weight(0.4f).verticalScroll(rememberScrollState())) {
            if (subjectId > 0) {
                NavHost(
                    navController = ceNavHostController,
                    startDestination = FULL_COMPOSE_EXAMINATION_ROUTE,
                    modifier = Modifier,
                ) {
                    composeExaminationScreen(
                        modifier = Modifier.padding(8.dp),
                        onShowSnack = onShowSnackbar,
                        onBack = {
                            ceNavHostController.popBackStack()
                            if (ceNavHostController.currentDestination == null) {
                                ceNavHostController.navigateToComposeExamination(
                                    subjectId,
                                    -1,
                                )
                            }
                        },
                        subjectId = subjectId,
                    )
                }
            }
        }
    }
}


@Composable
fun TopicPanel(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    subjectId: Long,
) {
    val topicNav = rememberNavController()
    val ctNav = rememberNavController()
    val screenModifier = modifier.fillMaxSize().padding(8.dp)

    Row(Modifier.fillMaxSize()) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = TOPIC_ROUTE,
            navController = topicNav,
        ) {
            topicScreen(
                modifier = screenModifier,
                onShowSnack = onShowSnackbar,
                subjectId = subjectId,
                navigateToComposeTopic = ctNav::navigateToComposeTopic,
            )
        }
        Column(Modifier.weight(0.4f)) {
            NavHost(
                navController = ctNav,
                startDestination = COMPOSE_TOPIC_ROUTE,
                modifier = Modifier,

                ) {
                composeTopicScreen(
                    modifier = screenModifier,
                    onShowSnack = onShowSnackbar,
                    onFinish = {
                        ctNav.popBackStack()
                        if (ctNav.currentDestination == null) {
                            ctNav.navigateToComposeTopic(
                                subjectId,
                                -1,
                            )
                        }
                    },
                    subjectId = subjectId,

                    )
            }
        }
    }
}
