/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.ui.ScreenSize
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberSeriesEditorAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SeriesEditorAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        SeriesEditorAppState(
            navController,
            coroutineScope,
            windowSizeClass,
        )
    }
}

@Stable
class SeriesEditorAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val screenSize
        get() = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> ScreenSize.COMPACT
            WindowWidthSizeClass.Medium -> ScreenSize.MEDIUM
            else -> ScreenSize.EXPANDED
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = false

    //            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//            mainRoute.contains(currentDestination?.route)
    val shouldShowNavRail: Boolean
        @Composable get() = false
//    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium &&
//            mainRoute.contains(currentDestination?.route)

    val shouldShowDrawer: Boolean
        @Composable get() = false
//    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//            mainRoute.contains(currentDestination?.route)

//    val isOffline = networkMonitor.isOnline
//        .map(Boolean::not)
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false,
//        )
}
//
// @Composable
// private fun NavigationTrackingSideEffect(navController: NavHostController) {
//    TrackDisposableJank(navController) { metricsHolder ->
//        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//            metricsHolder.state?.putState("Navigation", destination.route.toString())
//        }
//
//        navController.addOnDestinationChangedListener(listener)
//
//        onDispose {
//            navController.removeOnDestinationChangedListener(listener)
//        }
//    }
// }
