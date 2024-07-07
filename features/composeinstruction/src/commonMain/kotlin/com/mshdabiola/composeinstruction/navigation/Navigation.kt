/*
 *abiola 2022
 */

package com.mshdabiola.composeinstruction.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.composeinstruction.CiRoute

private const val CI_ROUTE = "ci_route"
private const val EXAM_ARG = "exam_arg"
private const val INSTRUCTION_ARG = "instruction_arg"

const val COMPOSE_INSTRUCTION_ROUTE="$CI_ROUTE/{$EXAM_ARG}/{$INSTRUCTION_ARG}"


fun NavController.navigateToComposeInstruction(examId:Long,instructionId:Long,navOptions: NavOptions= androidx.navigation.navOptions {  }) = navigate("$CI_ROUTE/$examId/$instructionId", navOptions)

fun NavGraphBuilder.composeInstructionScreen(
    modifier: Modifier=Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: ()->Unit,
    defaultExamId:Long=-1
) {

    composable(
        route = COMPOSE_INSTRUCTION_ROUTE ,
        arguments = listOf(
            navArgument(EXAM_ARG) {
                type = NavType.LongType
                defaultValue = defaultExamId

            },
            navArgument(INSTRUCTION_ARG) {
                type = NavType.LongType
            })
    ) {
        val examId = it.arguments?.getLong(EXAM_ARG) ?: -1L
        val instructionId = it.arguments?.getLong(INSTRUCTION_ARG) ?: -1L
        CiRoute(
            modifier=modifier,
            onShowSnack = onShowSnack,
            onFinish = onFinish,
            examId=examId,
            instructionId = instructionId
        )
    }
}
