/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.SUBJECT_ARG
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.serieseditor.ui.mainpanel.MAIN_PANEL_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberExtend(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController = rememberNavController(),
     subjectNavHostController:NavHostController = rememberNavController(),
    examNavHostController :NavHostController = rememberNavController()
): SeriesEditorAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        mainNavController,
        subjectNavHostController,
        examNavHostController
    ) {

        Extended(
                navController, coroutineScope, windowSizeClass, mainNavController, subjectNavHostController, examNavHostController
            )
    }
}

@Composable
fun rememberOther(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SeriesEditorAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass
    ) {

        Other(
            navController, coroutineScope, windowSizeClass
        )
    }
}

sealed class SeriesEditorAppState(
    open val navController: NavHostController,
    open val coroutineScope: CoroutineScope,
    open val windowSizeClass: WindowSizeClass,
){
   abstract val showMainTopBar: Boolean
       @Composable get

    abstract val showPermanentDrawer: Boolean
        @Composable get

    abstract val currentSubjectId :Long
        @Composable get

    abstract fun onSubjectClick(id:Long)
    abstract fun onUpdateSubject(id:Long)


}

class Extended(
    override val navController: NavHostController,
    override val coroutineScope: CoroutineScope,
    override val windowSizeClass: WindowSizeClass,
    val mainNavController: NavHostController,
    val subjectNavHostController: NavHostController,
    val examNavHostController: NavHostController

) : SeriesEditorAppState(navController, coroutineScope, windowSizeClass){

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    override val showMainTopBar: Boolean
      @Composable  get() =  currentDestination?.route?.contains(MAIN_PANEL_ROUTE) == true

    override val showPermanentDrawer: Boolean
      @Composable  get() = currentDestination?.route?.contains(MAIN_PANEL_ROUTE) == true

    override val currentSubjectId: Long
        @Composable get() = mainNavController
            .currentBackStackEntryAsState()
            .value
            ?.arguments
            ?.getLong(SUBJECT_ARG) ?: -1

    override fun onSubjectClick(id: Long) {
        mainNavController.navigateToMain(id)
    }

    override fun onUpdateSubject(id: Long) {
        subjectNavHostController.navigateToComposeSubject(id)
    }
}

class Other(
    override val navController: NavHostController,
    override val coroutineScope: CoroutineScope,
    override val windowSizeClass: WindowSizeClass,
): SeriesEditorAppState(navController, coroutineScope, windowSizeClass){

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    override val showMainTopBar: Boolean
        @Composable  get() =  currentDestination?.route?.contains(MAIN_ROUTE) == true

    override val currentSubjectId: Long
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value
            ?.arguments
            ?.getLong(SUBJECT_ARG) ?: -1

    override val showPermanentDrawer: Boolean
        @Composable  get() = when(windowSizeClass.widthSizeClass){
            WindowWidthSizeClass.Compact->false
            else-> true
        }

    override fun onSubjectClick(id: Long) {
        navController.navigateToMain(id)
    }

    override fun onUpdateSubject(id: Long) {
        navController.navigateToComposeSubject(id)
    }
}

