/*
 *abiola 2022
 */

package com.mshdabiola.composeexam.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.composeexam.ComposeExaminationRoute

const val COMPOSE_EXAMINATION_ROUTE = "compose_examination_route"
const val EXAM_ARG = "exam_arg"
const val FULL_COMPOSE_EXAMINATION_ROUTE = "$COMPOSE_EXAMINATION_ROUTE/{$EXAM_ARG}"

fun NavController.navigateToComposeExamination(
    examId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$COMPOSE_EXAMINATION_ROUTE/$examId", navOptions)

fun NavGraphBuilder.composeExaminationScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable(
        route = "$COMPOSE_EXAMINATION_ROUTE/{$EXAM_ARG}",
        arguments = listOf(
            navArgument(EXAM_ARG) {
                type = NavType.LongType
                defaultValue = -1L
            },
        ),
    ) {
        val examId = it.arguments?.getLong(EXAM_ARG) ?: -1L
        ComposeExaminationRoute(
            modifier = modifier,
            examId = examId,
            onBack = onBack,
            onShowSnack = onShowSnack,

            )
    }
}
