package com.mshdabiola.serieseditor.ui.subjectpanel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composesubject.navigation.FULL_CS_ROUTE
import com.mshdabiola.composesubject.navigation.composeSubjectScreen
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.serieseditor.ui.SeriesEditorAppState
import com.mshdabiola.serieseditor.ui.subjectitemspanel.navigateToSubjectItemPanel
import com.mshdabiola.subjects.navigation.FULL_SUBJECT_ROUTE
import com.mshdabiola.subjects.navigation.subjectScreen

@Composable
fun SubjectPaneScreen(
    modifier: Modifier = Modifier,
    appState: SeriesEditorAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    seriesId: Long,
) {
    val screenModifier = modifier.fillMaxSize().padding(8.dp)
    val subjectNavHostController = rememberNavController()
    val csNavHostController = rememberNavController()
    val isSmallScreen = appState.isSmallScreen

    Row(modifier) {
        NavHost(
            modifier = modifier.weight(0.6f),
            startDestination = FULL_SUBJECT_ROUTE,
            navController = subjectNavHostController,
        ) {
            subjectScreen(
                modifier = screenModifier,
                onShowSnack = onShowSnackbar,
                navigateToExam = appState.navController::navigateToSubjectItemPanel,
                updateSubject = { id1, id2 ->
                    if (isSmallScreen) {
                        appState.navController.navigateToComposeSubject(
                            id1,
                            id2,
                        )
                    } else {
                        csNavHostController.navigateToComposeSubject(id1, id2)
                    }
                },
                defaultSeriesId = seriesId,
            )
        }
        if (!isSmallScreen) {
            NavHost(
                navController = csNavHostController,
                startDestination = FULL_CS_ROUTE,
                modifier = Modifier.weight(0.4f),
            ) {
                composeSubjectScreen(
                    modifier = Modifier.padding(8.dp),
                    onShowSnack = onShowSnackbar,
                    onFinish = {
                        csNavHostController.popBackStack()
                        if (csNavHostController.currentDestination == null) {
                            csNavHostController.navigateToComposeSubject(
                                seriesId,
                                -1,
                            )
                        }
                    },
                    defaultSeriesId = seriesId,
                )
            }
        }
    }
}
