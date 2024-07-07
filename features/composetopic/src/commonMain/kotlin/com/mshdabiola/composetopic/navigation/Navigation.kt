/*
 *abiola 2022
 */

package com.mshdabiola.composetopic.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.composetopic.CtRoute

private const val ROUTE = "ct_route"
 const val SUBJECT_ARG = "subject_arg"
private const val TOPIC_ARG = "topic_arg"
const val COMPOSE_TOPIC_ROUTE="$ROUTE/{$SUBJECT_ARG}/{$TOPIC_ARG}"

fun NavController.navigateToComposeTopic(subjectId:Long,topicId:Long,navOptions: NavOptions= androidx.navigation.navOptions {  }) = navigate("$ROUTE/$subjectId/$topicId", navOptions)

fun NavGraphBuilder.composeTopicScreen(
    modifier: Modifier=Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: ()->Unit,
    subjectId: Long
) {
    composable(
        route = COMPOSE_TOPIC_ROUTE,
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue=subjectId
            },
            navArgument(TOPIC_ARG) {
                type = NavType.LongType
                defaultValue = -1L
            }
        )
    ) {
        val subjectId2 = it.arguments?.getLong(SUBJECT_ARG) ?: -1L
        val topicId = it.arguments?.getLong(TOPIC_ARG) ?: -1L
        println("subj $subjectId2 $topicId")
        CtRoute(
            modifier=modifier,
            onShowSnack = onShowSnack,
            onFinish = onFinish,
            subjectId = subjectId2,
            topicId = topicId
        )
    }
}
