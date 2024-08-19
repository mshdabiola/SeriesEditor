/*
 *abiola 2022
 */

package com.mshdabiola.subjects.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.subjects.SubjectRoute

const val SUBJECT_ROUTE = "subject_route"
const val SERIES_ID = "series_id"
const val FULL_SUBJECT_ROUTE = "$SUBJECT_ROUTE/{$SERIES_ID}"

fun NavController.navigateToSubjects(
    seriesId: Long,
    navOptions: NavOptions = androidx.navigation.navOptions {
        //  this.launchSingleTop = true
    },
) = navigate("$SUBJECT_ROUTE/$seriesId", navOptions)

fun NavGraphBuilder.subjectScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToExam: (Long) -> Unit,
    updateSubject: (Long, Long) -> Unit,
    defaultSeriesId: Long = -1L,

) {
    composable(
        route = FULL_SUBJECT_ROUTE,
        arguments = listOf(
            navArgument(SERIES_ID) {
                type = NavType.LongType
                defaultValue = defaultSeriesId
            },
        ),
    ) {
        val seriesId = it.arguments?.getLong(SERIES_ID) ?: -1L
        SubjectRoute(
            modifier = modifier,
            // onShowSnackbar = onShowSnack,
            navigateToQuestion = navigateToExam,
            updateSubjects = { updateSubject(seriesId, it) },
            seriesId = seriesId,
        )
    }
}
