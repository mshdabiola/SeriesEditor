package com.mshdabiola.serieseditor.ui.mainpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


const val MAIN_PANEL_ROUTE = "main_panel_route"
const val SUBJECT_ID = "subject_id"
const val DEFAULT_MAIN_PANEL_ROUTE = "$MAIN_PANEL_ROUTE/{$SUBJECT_ID}"

fun NavController.navigateToMainPanel(subjectId: Long, navOptions: NavOptions = androidx.navigation.navOptions {  }) = navigate("$MAIN_PANEL_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.mainPanelScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = "$MAIN_PANEL_ROUTE/{$SUBJECT_ID}",
        arguments = listOf(
            navArgument(SUBJECT_ID) {
            type = NavType.LongType
            defaultValue=-1L
        }
        )
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ID) ?: -1L
        MainPaneScreen(
            modifier=modifier,
            subjectId=subjectId,
            navigateToQuestion = {}
            // onShowSnackbar = onShowSnack,
        )
    }
}
