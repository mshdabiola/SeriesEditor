/*
 *abiola 2022
 */

package com.mshdabiola.main.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.main.MainRoute

const val MAIN_ROUTE = "main_route"
const val SUBJECT_ARG="subject_arg"
const val DEFAULT_ROUTE="$MAIN_ROUTE/{$SUBJECT_ARG}"

fun NavController.navigateToMain(subjectId:Long,navOptions: NavOptions= androidx.navigation.navOptions {  }) = navigate("$MAIN_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.mainScreen(
    modifier: Modifier=Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToQuestion: (Long) -> Unit,
    updateExam : (Long)->Unit,

    ) {
    composable(
        route = "$MAIN_ROUTE/{$SUBJECT_ARG}",
        arguments = listOf(
            navArgument(SUBJECT_ARG) {
                type = NavType.LongType
                defaultValue=-1L
            }
        )
    ) {curr->
        val currentSubjectId=curr.arguments?.getLong(SUBJECT_ARG)?:-1L
        MainRoute(
            modifier=modifier,
           // onShowSnackbar = onShowSnack,
            subjectId =currentSubjectId,
            navigateToQuestion = navigateToQuestion,
            updateExam = updateExam
        )
    }
}
