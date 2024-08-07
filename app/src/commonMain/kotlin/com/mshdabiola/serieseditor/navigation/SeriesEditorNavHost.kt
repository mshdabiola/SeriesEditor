/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.mshdabiola.composeexam.navigation.composeExaminationScreen
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.composeinstruction.navigation.composeInstructionScreen
import com.mshdabiola.composeinstruction.navigation.navigateToComposeInstruction
import com.mshdabiola.composequestion.navigation.composeQuestionScreen
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.composetopic.navigation.composeTopicScreen
import com.mshdabiola.composetopic.navigation.navigateToComposeTopic
import com.mshdabiola.main.navigation.DEFAULT_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.exampanel.examPanelScreen
import com.mshdabiola.serieseditor.ui.exampanelother.examPanelScreen
import com.mshdabiola.serieseditor.ui.exampanelother.navigateToExamPanel
import com.mshdabiola.serieseditor.ui.mainpanel.MAIN_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.mainpanel.mainPanelScreen
import com.mshdabiola.serieseditor.ui.topicpanel.navigateToTopicPanel
import com.mshdabiola.serieseditor.ui.topicpanel.topicPanelScreen
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.topics.navigation.navigateToTopic
import com.mshdabiola.topics.navigation.topicScreen

@Composable
fun ExtendNavHost(
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_PANEL_ROUTE,
) {
    val navController = appState.navController
    val screenModifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainPanelScreen(
            onShowSnack = onShowSnackbar,
            appState = appState,
        )
        examPanelScreen(
            modifier = Modifier,
            onShowSnack = onShowSnackbar,
            navigateToTopicPanel = navController::navigateToTopicPanel,

        )
        topicPanelScreen(
            modifier,
            onShowSnackbar,
        )
        settingScreen(
            modifier = Modifier,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OtherNavHost(
    appState: Other,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = DEFAULT_ROUTE,
) {
    val navController = appState.navController
    val screenModifier = modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .windowInsetsPadding(WindowInsets.systemBars)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToQuestion = navController::navigateToExamPanel,
            updateExam = navController::navigateToComposeExamination,
        )
        composeSubjectScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onFinish = navController::popBackStack,
        )
        composeExaminationScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
            onAddSubject = { navController.navigateToComposeSubject(-1) },
        )

        examPanelScreen(
            modifier = Modifier.fillMaxSize(),
            onShowSnack = onShowSnackbar,
            appState = appState,
        )
        composeQuestionScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToInstruction = navController::navigateToComposeInstruction,
            navigateToTopic = navController::navigateToTopic,
            onFinish = { navController.popBackStack() },
        )

        composeInstructionScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onFinish = navController::popBackStack,
        )
        topicScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToComposeTopic = navController::navigateToComposeTopic,
            subjectId = -1,
        )
        composeTopicScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onFinish = navController::popBackStack,
            subjectId = -1,
        )
        settingScreen(
            modifier = Modifier,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,

        )
    }
}
