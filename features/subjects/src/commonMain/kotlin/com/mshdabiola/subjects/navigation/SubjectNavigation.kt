/*
 *abiola 2022
 */

package com.mshdabiola.subjects.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.subjects.SubjectRoute

const val SUBJECT_ROUTE = "subject_route"

fun NavController.navigateToSubjects(
    navOptions: NavOptions = androidx.navigation.navOptions {
        //  this.launchSingleTop = true
    },
) = navigate(SUBJECT_ROUTE, navOptions)

fun NavGraphBuilder.subjectScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToExam: (Long) -> Unit,
    updateSubject: (Long) -> Unit,

) {
    composable(
        route = SUBJECT_ROUTE,
    ) {
        SubjectRoute(
            modifier = modifier,
            // onShowSnackbar = onShowSnack,
            navigateToQuestion = navigateToExam,
            updateSubjects = updateSubject,
        )
    }
}
