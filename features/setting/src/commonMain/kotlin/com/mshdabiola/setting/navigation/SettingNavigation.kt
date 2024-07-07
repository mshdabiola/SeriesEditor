/*
 *abiola 2022
 */

package com.mshdabiola.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.settingRoute
import com.mshdabiola.setting.SettingRoute
import com.mshdabiola.setting.SettingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

val SETTING_ROUTE = settingRoute[0]

fun NavController.navigateToSetting() = navigate(SETTING_ROUTE)

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable(route = SETTING_ROUTE) {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            modifier = modifier,
            onBack = onBack,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
        )
    }
}
