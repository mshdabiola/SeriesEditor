/*
 *abiola 2022
 */

package com.mshdabiola.questions.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.questions.QuestionsRoute

private const val ROUTE = "question_route"
private const val EXAM_ARG = "exam_arg"
const val QUESTIONS_ROUTE="$ROUTE/{$EXAM_ARG}"

fun NavController.navigateToQuestion(examId: Long, navOptions: NavOptions= androidx.navigation.navOptions {  }) =
    navigate("$ROUTE/$examId", navOptions)

fun NavGraphBuilder.questionScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    defaultExamId:Long=-1,
    navigateToComposeQuestion: (Long,Long) -> Unit,

) {
    composable(
        route = QUESTIONS_ROUTE ,
        arguments = listOf(
            navArgument(EXAM_ARG) {
                type = NavType.LongType
                defaultValue = defaultExamId
            },
        ),
    ) { stackEntry ->
        val examId = stackEntry.arguments?.getLong(EXAM_ARG) ?: -1L
        QuestionsRoute(
            modifier = modifier,
            navigateToComposeQuestion = { navigateToComposeQuestion(examId,it) },
            examId = examId)
    }
}
