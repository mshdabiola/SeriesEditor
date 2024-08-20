package com.mshdabiola.serieseditor.ui.subjectpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.serieseditor.ui.Extended

const val SUBJECT_PANEL_ROUTE = "subject_panel_route"
const val SERIES_ID = "series_id"
const val FULL_SUBJECT_PANEL_ROUTE = "$SUBJECT_PANEL_ROUTE/{$SERIES_ID}"

fun NavController.navigateToSubjectPanel(
    seriesId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$SUBJECT_PANEL_ROUTE/$seriesId", navOptions)

fun NavGraphBuilder.subjectPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = FULL_SUBJECT_PANEL_ROUTE,
        arguments = listOf(
            navArgument(SERIES_ID) {
                type = NavType.LongType
            },
        ),
    ) {
        val seriesId = it.arguments?.getLong(SERIES_ID) ?: -1L
        SubjectPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
            seriesId = seriesId,
        )
    }
}
