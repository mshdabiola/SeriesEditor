package com.mshdabiola.serieseditor.ui.mainpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

    Row(modifier) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = DEFAULT_ROUTE,
            navController = appState.mainNavController,
        ) {
            mainScreen(
                modifier = Modifier.padding(horizontal = 16.dp),
                onShowSnack = onShowSnackbar,
                navigateToQuestion = appState.navController::navigateToExamPanel,
                updateExam = appState.examNavHostController::navigateToComposeExamination,
            )

        }
        Column(Modifier.weight(0.4f)) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Examination Section ",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.heightIn(8.dp))
            NavHost(
                navController = appState.examNavHostController,
                startDestination = FULL_COMPOSE_EXAMINATION_ROUTE,
                modifier = Modifier,
            ) {
                composeExaminationScreen(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onShowSnack = onShowSnackbar,
                    onBack = {
                        appState.examNavHostController.popBackStack()
                        if (appState.examNavHostController.currentDestination == null) {
                            appState.examNavHostController.navigateToComposeExamination(-1)
                        }
                    },
                )

            }
            Spacer(Modifier.heightIn(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Subject Section ",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.heightIn(8.dp))

            NavHost(
                navController = appState.subjectNavHostController,
                startDestination = FULL_CS_ROUTE,
                modifier = Modifier,
            ) {
                composeSubjectScreen(
                    modifier = Modifier
                        .fillMaxWidth(),
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