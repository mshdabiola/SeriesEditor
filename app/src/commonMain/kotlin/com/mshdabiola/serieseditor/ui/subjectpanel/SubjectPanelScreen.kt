package com.mshdabiola.serieseditor.ui.subjectpanel

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
import com.mshdabiola.composesubject.navigation.FULL_CS_ROUTE
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.exampanel.navigateToExamPanel
import com.mshdabiola.subjects.navigation.SUBJECT_ROUTE
import com.mshdabiola.subjects.navigation.subjectScreen

@Composable
fun SubjectPaneScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
) {
    val screenModifier = modifier.fillMaxSize().padding(8.dp)

    Row(modifier) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = SUBJECT_ROUTE,
            navController = appState.mainNavController,
        ) {
            subjectScreen(
                modifier = screenModifier,
                onShowSnack = onShowSnackbar,
                navigateToExam = { appState.navController::navigateToExamPanel },
                updateSubject = {},
            )
        }
        Column(Modifier.weight(0.4f).verticalScroll(rememberScrollState())) {
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
