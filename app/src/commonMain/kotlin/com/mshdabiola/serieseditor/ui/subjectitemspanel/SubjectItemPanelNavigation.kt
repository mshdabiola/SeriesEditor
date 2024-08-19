package com.mshdabiola.serieseditor.ui.subjectitemspanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.serieseditor.ui.Extended
import com.mshdabiola.serieseditor.ui.Other

const val SUBJECT_ITEM_PANEL_ROUTE = "subject_item_panel_route"
const val SUBJECT_ARG = "subject_arg"
const val DEFAULT_SUBJECT_ITEM_PANEL_ROUTE = "$SUBJECT_ITEM_PANEL_ROUTE/{$SUBJECT_ARG}"

fun NavController.navigateToSubjectItemPanel(
    subjectId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$SUBJECT_ITEM_PANEL_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.subjectItemPanelScreen(
    modifier: Modifier = Modifier,
    appState: Extended,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = DEFAULT_SUBJECT_ITEM_PANEL_ROUTE,
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue = -1
            },
        ),
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ARG) ?: -1L
        SubjectItemPaneScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
            subjectId = subjectId,
        )
    }
}

fun NavGraphBuilder.subjectItemPanelOtherScreen(
    modifier: Modifier = Modifier,
    appState: Other,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = DEFAULT_SUBJECT_ITEM_PANEL_ROUTE,
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue = -1
            },
        ),
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ARG) ?: -1L
        SubjectItemPaneOtherScreen(
            modifier = modifier,
            appState = appState,
            onShowSnackbar = onShowSnack,
            subjectId = subjectId,
        )
    }
}
