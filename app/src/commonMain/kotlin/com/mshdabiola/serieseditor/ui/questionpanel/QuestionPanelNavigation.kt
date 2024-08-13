package com.mshdabiola.serieseditor.ui.questionpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val QUESTION_PANEL_ROUTE = "question_panel_route"
const val QUESTION_PANEL_ARG = "question_arg"

fun NavController.navigateToQuestionPanel(
    examId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$QUESTION_PANEL_ROUTE/$examId", navOptions)

fun NavGraphBuilder.questionPanelScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToTopicPanel: (Long) -> Unit,

) {
    composable(
        route = "$QUESTION_PANEL_ROUTE/{$QUESTION_PANEL_ARG}",
        arguments = listOf(
            navArgument(QUESTION_PANEL_ARG) { type = NavType.LongType },
        ),
    ) {
        val examId = it.arguments?.getLong(QUESTION_PANEL_ARG) ?: -1
        QuestionPaneScreen(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            examId = examId,
            navigateToTopicPanel = navigateToTopicPanel,
        )
    }
}
