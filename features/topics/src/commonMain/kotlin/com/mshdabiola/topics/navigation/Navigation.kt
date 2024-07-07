/*
 *abiola 2022
 */

package com.mshdabiola.topics.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.topics.TopicsRoute

private const val ROUTE = "topic_route"
private const val SUBJECT_ARG = "subject_arg"
const val TOPIC_ROUTE = "$ROUTE/{$SUBJECT_ARG}"

fun NavController.navigateToTopic(
    subjectId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.topicScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToComposeTopic: (Long, Long) -> Unit,
    subjectId: Long,
) {
    composable(
        route = TOPIC_ROUTE,
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue = subjectId
            },
        ),
    ) { // stackEntry ->
        // val subjectId = stackEntry.arguments?.getLong(SUBJECT_ARG) ?: -1L
        TopicsRoute(
            modifier = modifier,
            navigateToComposeTopic = { navigateToComposeTopic(subjectId, it) },
            subjectId = subjectId,
        )
    }
}
