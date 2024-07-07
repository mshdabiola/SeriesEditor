package com.mshdabiola.serieseditor.ui.mainpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.serieseditor.ui.Extended

const val MAIN_PANEL_ROUTE = "main_panel_route"

fun NavController.navigateToMainPanel(navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate(MAIN_PANEL_ROUTE, navOptions)

fun NavGraphBuilder.mainPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = MAIN_PANEL_ROUTE,
    ) {
        MainPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
        )
    }
}
