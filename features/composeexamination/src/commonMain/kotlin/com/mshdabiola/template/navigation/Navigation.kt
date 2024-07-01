/*
 *abiola 2022
 */

package com.mshdabiola.template.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.template.ComposeExaminationRoute

const val COMPOSE_EXAMINATION_ROUTE = "compose_examination_route"
const val EXAM_ARG = "exam_arg"

fun NavController.navigateToComposeExamination(examId: Long, navOptions: NavOptions= androidx.navigation.navOptions {  }) =
    navigate("$COMPOSE_EXAMINATION_ROUTE/$examId", navOptions)

fun NavGraphBuilder.composeExaminationScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
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
            // onShowSnackbar = onShowSnack,
        )
    }
}
