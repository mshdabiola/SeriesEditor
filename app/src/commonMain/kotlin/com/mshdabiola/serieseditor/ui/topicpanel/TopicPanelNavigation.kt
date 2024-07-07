package com.mshdabiola.serieseditor.ui.topicpanel

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


const val TOPIC_PANEL_ROUTE = "topic_panel_route"
const val SUBJECT_ARG = "subject_arg"

fun NavController.navigateToTopicPanel(subjectId: Long, navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate("$TOPIC_PANEL_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.topicPanelScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable(
        route = "$TOPIC_PANEL_ROUTE/{$SUBJECT_ARG}",
        arguments = listOf(
            navArgument(SUBJECT_ARG) { type = NavType.LongType })
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ARG) ?: -1
        TopicPaneScreen(
            modifier = modifier,
            onShowSnackbar = onShowSnack,
            subjectId = subjectId
        )
    }
}
