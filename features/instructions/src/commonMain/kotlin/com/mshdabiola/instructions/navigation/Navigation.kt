/*
 *abiola 2022
 */

package com.mshdabiola.instructions.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.instructions.InstructionsRoute
import kotlin.reflect.KFunction3

private const val ROUTE = "instruction_route"
private const val EXAM_ARG = "exam_arg"
const val INSTRUCTION_ROUTE = "$ROUTE/{$EXAM_ARG}"

fun NavController.navigateToInstructions(
    examId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) =
    navigate("$ROUTE/$examId", navOptions)

fun NavGraphBuilder.instructionScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    defaultExamId: Long = -1,
    navigateToComposeInstruction: (Long, Long) -> Unit,
) {
    composable(
        route = INSTRUCTION_ROUTE,
        arguments = listOf(
            navArgument(EXAM_ARG) {
                type = NavType.LongType
                defaultValue = defaultExamId
            },
        ),
    ) { stackEntry ->
        val examId = stackEntry.arguments?.getLong(EXAM_ARG) ?: -1L
        InstructionsRoute(
            modifier = modifier,
            navigateToComposeInstruction = { navigateToComposeInstruction(examId, it) },
            examId = examId,
        )
    }
}
