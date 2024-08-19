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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.mshdabiola.composeexam.navigation.composeExaminationScreen
import com.mshdabiola.composeinstruction.navigation.composeInstructionScreen
import com.mshdabiola.composequestion.navigation.composeQuestionScreen
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.composetopic.navigation.composeTopicScreen
import com.mshdabiola.login.navigation.LOGIN_ROUTE
import com.mshdabiola.login.navigation.loginScreen
import com.mshdabiola.login.navigation.navigateToLogin
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.examItemspanel.examItemOtherPanelScreen
import com.mshdabiola.serieseditor.ui.examItemspanel.examItemPanelScreen
import com.mshdabiola.serieseditor.ui.subjectitemspanel.navigateToSubjectItemPanel
import com.mshdabiola.serieseditor.ui.subjectitemspanel.subjectItemPanelOtherScreen
import com.mshdabiola.serieseditor.ui.subjectitemspanel.subjectItemPanelScreen
import com.mshdabiola.serieseditor.ui.subjectpanel.navigateToSubjectPanel
import com.mshdabiola.serieseditor.ui.subjectpanel.subjectPanelScreen
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.subjects.navigation.navigateToSubjects
import com.mshdabiola.subjects.navigation.subjectScreen

@Composable
fun ExtendNavHost(
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
    userId: Long = -1L,
) {
    val navController = appState.navController
    val screenModifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)
    LaunchedEffect(userId) {
        if (userId == -1L) {
            appState.navController.navigateToLogin(
                navOptions = navOptions {
                    popUpTo(MAIN_ROUTE) {
                        inclusive = true
                    }
                },
            )
        } else {
            appState.navController.navigateToMain(
                navOptions = navOptions {
                    popUpTo(LOGIN_ROUTE) {
                        inclusive = true
                    }
                },
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(screenModifier)
        mainScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onNavigateToSubject = navController::navigateToSubjectPanel,
        )
        subjectPanelScreen(
            appState = appState,
            onShowSnack = onShowSnackbar,
        )
        subjectItemPanelScreen(
            onShowSnack = onShowSnackbar,
            appState = appState,
        )
        examItemPanelScreen(
            modifier = Modifier,
            onShowSnack = onShowSnackbar,
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
    userId: Long = -1L,

) {
    val navController = appState.navController
    val screenModifier = modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .windowInsetsPadding(WindowInsets.systemBars)

    LaunchedEffect(userId) {
        if (userId == -1L) {
            appState.navController.navigateToLogin(
                navOptions = navOptions {
                    popUpTo(MAIN_ROUTE) {
                        inclusive = true
                    }
                },
            )
        } else {
            appState.navController.navigateToMain(
                navOptions = navOptions {
                    popUpTo(LOGIN_ROUTE) {
                        inclusive = true
                    }
                },
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginScreen(screenModifier)
        mainScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onNavigateToSubject = navController::navigateToSubjects,

        )

        subjectScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            navigateToExam = navController::navigateToSubjectItemPanel,
            updateSubject = navController::navigateToComposeSubject,
        )

        subjectItemPanelOtherScreen(
            onShowSnack = onShowSnackbar,
            appState = appState,
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

        examItemOtherPanelScreen(
            modifier = Modifier,
            onShowSnack = onShowSnackbar,
            appState = appState,
        )
        composeQuestionScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onFinish = { navController.popBackStack() },
        )

        composeInstructionScreen(
            modifier = screenModifier,
            onShowSnack = onShowSnackbar,
            onFinish = navController::popBackStack,
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
