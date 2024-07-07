/*
 *abiola 2022
 */

package com.mshdabiola.composequestion.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.composequestion.CqRoute

private const val ROUTE = "cq_route"
const val EXAM_ARG = "exam_arg"
private const val QUESTION_ARG = "question_arg"
const val COMPOSE_QUESTION_ROUTE = "$ROUTE/{$EXAM_ARG}/{$QUESTION_ARG}"

fun NavController.navigateToComposeQuestion(
    examId: Long,
    questionId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate(route = "$ROUTE/$examId/$questionId", navOptions = navOptions)

fun NavGraphBuilder.composeQuestionScreen(
    modifier: Modifier = Modifier,
    defaultExamId: Long = -1,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToTopic: (Long, Long) -> Unit,
    navigateToInstruction: (Long, Long) -> Unit,
    onFinish: () -> Unit,

) {
    composable(
        route = COMPOSE_QUESTION_ROUTE,
        arguments = listOf(
            navArgument(EXAM_ARG) {
                type = NavType.LongType
                defaultValue = defaultExamId
            },
            navArgument(QUESTION_ARG) {
                type = NavType.LongType
                defaultValue = -1
            },
        ),
    ) {
        val examId = it.arguments?.getLong(EXAM_ARG) ?: -1L
        val questionId = it.arguments?.getLong(QUESTION_ARG) ?: -1L
        CqRoute(
            modifier = modifier,
            onShowSnack = onShowSnack,
            onFinish = onFinish,
            examId = examId,
            questionId = questionId,
            navigateToTopic = navigateToTopic,
            navigateToInstruction = navigateToInstruction,
        )
    }
}
