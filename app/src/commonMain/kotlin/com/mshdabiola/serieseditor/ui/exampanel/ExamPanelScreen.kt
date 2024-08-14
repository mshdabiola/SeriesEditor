package com.mshdabiola.serieseditor.ui.exampanel

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
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composeexam.navigation.FULL_COMPOSE_EXAMINATION_ROUTE
import com.mshdabiola.composeexam.navigation.composeExaminationScreen
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.examinations.navigation.DEFAULT_ROUTE
import com.mshdabiola.examinations.navigation.examScreen
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.questionpanel.navigateToQuestionPanel

@Composable
fun ExamPaneScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
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
                navigateToQuestion = appState.navController::navigateToQuestionPanel,
                updateExam = ceNavHostController::navigateToComposeExamination,
                subjectId = subjectId,
            )
        }
        Column(Modifier.weight(0.4f).verticalScroll(rememberScrollState())) {
            if(subjectId>0) {
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
