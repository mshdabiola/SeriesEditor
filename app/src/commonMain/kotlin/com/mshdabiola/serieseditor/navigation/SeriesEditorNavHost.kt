/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.navigation

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
import com.mshdabiola.examinations.navigation.examScreen
import com.mshdabiola.examinations.navigation.navigateToExam
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.exampanel.examPanelScreen
import com.mshdabiola.serieseditor.ui.questionpanel.questionPanelScreen
import com.mshdabiola.serieseditor.ui.questionpanelother.navigateToQuestionPanelOther
import com.mshdabiola.serieseditor.ui.questionpanelother.questionPanelOtherScreen
import com.mshdabiola.serieseditor.ui.subjectpanel.navigateToSubjectPanel
import com.mshdabiola.serieseditor.ui.subjectpanel.subjectPanelScreen
import com.mshdabiola.serieseditor.ui.topicpanel.navigateToTopicPanel
import com.mshdabiola.serieseditor.ui.topicpanel.topicPanelScreen
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.subjects.navigation.navigateToSubjects
import com.mshdabiola.subjects.navigation.subjectScreen
import com.mshdabiola.topics.navigation.navigateToTopic
import com.mshdabiola.topics.navigation.topicScreen

@Composable
fun ExtendNavHost(
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
) {
    val navController = appState.navController
    val screenModifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onNavigateToSubject = navController::navigateToSubjectPanel,
        )
        subjectPanelScreen(
            appState = appState,
            onShowSnack = onShowSnackbar,
        )
        examPanelScreen(
            onShowSnack = onShowSnackbar,
            appState = appState,
        )
        questionPanelScreen(
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

@Composable
fun OtherNavHost(
    appState: Other,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
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
            onNavigateToSubject = navController::navigateToSubjects,

        )

        subjectScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToExam = navController::navigateToExam,
            updateSubject = navController::navigateToComposeSubject,
        )

        examScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToQuestion = navController::navigateToQuestionPanelOther,
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
        )

        questionPanelOtherScreen(
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
