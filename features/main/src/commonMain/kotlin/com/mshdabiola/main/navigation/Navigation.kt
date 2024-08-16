/*
 *abiola 2022
 */

package com.mshdabiola.main.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.main.MainRoute

const val MAIN_ROUTE = "main_route"

fun NavController.navigateToMain(
    navOptions: NavOptions = androidx.navigation.navOptions {
    },
) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onNavigateToSubject: (Long) -> Unit,
) {
    composable(
        route = MAIN_ROUTE,
    ) {
        MainRoute(
            modifier = modifier,
            onShowSnack = onShowSnack,
            navigateToSubject = onNavigateToSubject,
        )
    }
}
