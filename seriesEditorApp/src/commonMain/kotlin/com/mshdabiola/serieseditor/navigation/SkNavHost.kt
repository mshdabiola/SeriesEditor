/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.main.navigation.DEFAULT_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other
import com.mshdabiola.serieseditor.ui.mainpanel.MAIN_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.mainpanel.mainPanelScreen
import com.mshdabiola.topics.navigation.composeExaminationScreen
import com.mshdabiola.topics.navigation.navigateToComposeExamination

@Composable
fun ExtendNavHost(
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_PANEL_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
//        mainScreen(
//            onShowSnack = onShowSnackbar,
//            navigateToQuestion = {},
//        )
//        detailScreen(
//            screenSize = appState.screenSize,
//            onShowSnack = onShowSnackbar,
//            onBack = navController::popBackStack,
//        )
//        settingScreen(
//           // screenSize = appState.screenSize,
//            onShowSnack = onShowSnackbar,
//            onBack = navController::popBackStack,
//        )
//        composeSubjectScreen(
//            onShowSnack = onShowSnackbar
//        )
//
//        composeExaminationScreen(
//            onShowSnack = onShowSnackbar
//        )
//
        mainPanelScreen(
            onShowSnack = onShowSnackbar,
            appState = appState
        )

    }
}


@Composable
fun OtherNavHost(
    appState: Other,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = DEFAULT_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(
            onShowSnack = onShowSnackbar,
            navigateToQuestion = {},
            updateExam = navController::navigateToComposeExamination
        )
        composeSubjectScreen(
            modifier=Modifier.fillMaxSize(),
            onShowSnack = onShowSnackbar,
            onFinish = navController::popBackStack,
        )
        composeExaminationScreen(
            modifier=Modifier.fillMaxSize(),
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack
        )
    }
}
