package com.mshdabiola.serieseditor.ui.exampanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.serieseditor.ui.Extended

const val EXAM_PANEL_ROUTE = "exam_panel_route"
const val SUBJECT_ARG = "subject_arg"
const val DEFAULT_EXAM_PANEL_ROUTE = "$EXAM_PANEL_ROUTE/{$SUBJECT_ARG}"

fun NavController.navigateToExamPanel(subjectId: Long, navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate("$EXAM_PANEL_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.examPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = DEFAULT_EXAM_PANEL_ROUTE,
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue = -1
            },
        ),
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ARG) ?: -1L
        ExamPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
            subjectId = subjectId,
        )
    }
}
