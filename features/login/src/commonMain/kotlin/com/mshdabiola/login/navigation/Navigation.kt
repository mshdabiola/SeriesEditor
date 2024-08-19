/*
 *abiola 2022
 */

package com.mshdabiola.login.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.login.LoginRoute

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(
    navOptions: NavOptions = androidx.navigation.navOptions {
    },
) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen(
    modifier: Modifier = Modifier,
) {
    composable(
        route = LOGIN_ROUTE,
    ) {
        LoginRoute(
            modifier = modifier,
        )
    }
}
