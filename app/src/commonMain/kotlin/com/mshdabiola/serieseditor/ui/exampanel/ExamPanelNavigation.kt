package com.mshdabiola.serieseditor.ui.exampanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.serieseditor.ui.Extended

const val EXAM_PANEL_ROUTE = "exam_panel_route"

fun NavController.navigateToExamPanel(navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate(EXAM_PANEL_ROUTE, navOptions)

fun NavGraphBuilder.examPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = EXAM_PANEL_ROUTE,
    ) {
        ExamPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
        )
    }
}
