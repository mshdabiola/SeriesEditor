package com.mshdabiola.serieseditor.ui.examItemspanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.serieseditor.ui.SeriesEditorAppState

const val EXAM_ITEM_PANEL_ROUTE = "question_panel_route"
const val EXAM_ITEM_ARG = "examitem_arg"

fun NavController.navigateToExamItemPanel(

    examId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$EXAM_ITEM_PANEL_ROUTE/$examId", navOptions)

fun NavGraphBuilder.examItemPanelScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    appState: SeriesEditorAppState,
) {
    composable(
        route = "$EXAM_ITEM_PANEL_ROUTE/{$EXAM_ITEM_ARG}",
        arguments = listOf(
            navArgument(EXAM_ITEM_ARG) { type = NavType.LongType },
        ),
    ) {
        val examId = it.arguments?.getLong(EXAM_ITEM_ARG) ?: -1
        ExamItemPaneScreen(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            examId = examId,
            appState = appState,
        )
    }
}
