package com.mshdabiola.serieseditor.ui.subjectpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.serieseditor.ui.Extended

const val SUBJECT_PANEL_ROUTE = "subject_panel_route"

fun NavController.navigateToSubjectPanel(navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate(SUBJECT_PANEL_ROUTE, navOptions)

fun NavGraphBuilder.subjectPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = SUBJECT_PANEL_ROUTE,
    ) {
        SubjectPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
        )
    }
}
