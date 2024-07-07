package com.mshdabiola.serieseditor.ui.exampanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


const val EXAM_PANEL_ROUTE = "exam_panel_route"
const val EXAM_ARG = "exam_arg"

fun NavController.navigateToExamPanel(examId: Long,navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate("$EXAM_PANEL_ROUTE/$examId", navOptions)

fun NavGraphBuilder.examPanelScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToTopicPanel: (Long) ->Unit,

    ) {
    composable(
        route = "$EXAM_PANEL_ROUTE/{${EXAM_ARG}}",
        arguments = listOf(
            navArgument(EXAM_ARG) { type = NavType.LongType })
    ) {
        val examId = it.arguments?.getLong(EXAM_ARG) ?: -1
        ExamPaneScreen(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            examId=examId,
            navigateToTopicPanel = navigateToTopicPanel
        )
    }
}
