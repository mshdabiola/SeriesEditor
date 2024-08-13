package com.mshdabiola.serieseditor.ui.questionpanelother

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.serieseditor.ui.Other

const val QUESTION_PANEL_ROUTE = "question_panel_route"
const val QUESTION_PANEL_OTHER_ARG = "question_arg"

fun NavController.navigateToQuestionPanelOther(
    examId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$QUESTION_PANEL_ROUTE/$examId", navOptions)

fun NavGraphBuilder.questionPanelOtherScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    appState: Other,

) {
    composable(
        route = "$QUESTION_PANEL_ROUTE/{$QUESTION_PANEL_OTHER_ARG}",
        arguments = listOf(
            navArgument(QUESTION_PANEL_OTHER_ARG) { type = NavType.LongType },
        ),
    ) {
        val examId = it.arguments?.getLong(QUESTION_PANEL_OTHER_ARG) ?: -1
        QuestionPaneScreen(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            examId = examId,
            appState = appState,
        )
    }
}
