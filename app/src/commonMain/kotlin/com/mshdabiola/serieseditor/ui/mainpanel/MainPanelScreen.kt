package com.mshdabiola.serieseditor.ui.mainpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.mshdabiola.composeexam.navigation.FULL_COMPOSE_EXAMINATION_ROUTE
import com.mshdabiola.composeexam.navigation.composeExaminationScreen
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.composesubject.navigation.FULL_CS_ROUTE
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.main.navigation.DEFAULT_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.exampanel.navigateToExamPanel

@Composable
fun MainPaneScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
) {
    val screenModifier = modifier.fillMaxSize().padding(8.dp)

    Row(modifier) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = DEFAULT_ROUTE,
            navController = appState.mainNavController,
        ) {
            mainScreen(
                modifier = screenModifier,
                onShowSnack = onShowSnackbar,
                navigateToQuestion = appState.navController::navigateToExamPanel,
                updateExam = appState.examNavHostController::navigateToComposeExamination,
            )
        }
        Column(Modifier.weight(0.4f).verticalScroll(rememberScrollState())) {
            NavHost(
                navController = appState.examNavHostController,
                startDestination = FULL_COMPOSE_EXAMINATION_ROUTE,
                modifier = Modifier,
            ) {
                composeExaminationScreen(
                    modifier = Modifier.padding(8.dp),
                    onShowSnack = onShowSnackbar,
                    onBack = {
                        appState.examNavHostController.popBackStack()
                        if (appState.examNavHostController.currentDestination == null) {
                            appState.examNavHostController.navigateToComposeExamination(-1)
                        }
                    },
                    null,
                )
            }

            NavHost(
                navController = appState.subjectNavHostController,
                startDestination = FULL_CS_ROUTE,
                modifier = Modifier,
            ) {
                composeSubjectScreen(
                    modifier = Modifier.padding(8.dp),
                    onShowSnack = onShowSnackbar,
                    onFinish = {
                        appState.subjectNavHostController.popBackStack()
                        if (appState.subjectNavHostController.currentDestination == null) {
                            appState.subjectNavHostController.navigateToComposeSubject(-1)
                        }
                    },
                )
            }
        }
    }
}
