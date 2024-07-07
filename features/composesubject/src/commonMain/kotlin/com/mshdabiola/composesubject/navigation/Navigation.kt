/*
 *abiola 2022
 */

package com.mshdabiola.composesubject.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.composesubject.SubjectRoute

const val CS_ROUTE = "cs_route"
const val SUBJECT_ID = "subject_id"
const val FULL_CS_ROUTE = "$CS_ROUTE/{$SUBJECT_ID}"

fun NavController.navigateToComposeSubject(
    subjectId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions { },
) = navigate("$CS_ROUTE/$subjectId", navOptions)

fun NavGraphBuilder.composeSubjectScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onFinish: () -> Unit,
) {
    composable(
        route = "$CS_ROUTE/{$SUBJECT_ID}",
        arguments = listOf(
            navArgument(SUBJECT_ID) {
                type = NavType.LongType
                defaultValue = -1L
            },
        ),
    ) {
        val subjectId = it.arguments?.getLong(SUBJECT_ID) ?: -1L
        SubjectRoute(
            modifier = modifier,
            subjectId = subjectId,
            onShowSnack = onShowSnack,
            onFinish = onFinish,
        )
    }
}
