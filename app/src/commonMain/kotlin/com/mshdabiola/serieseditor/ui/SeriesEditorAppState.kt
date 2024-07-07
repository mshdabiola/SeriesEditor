/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.composeinstruction.navigation.navigateToComposeInstruction
import com.mshdabiola.composequestion.navigation.EXAM_ARG
import com.mshdabiola.composequestion.navigation.navigateToComposeQuestion
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.composetopic.navigation.navigateToComposeTopic
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.SUBJECT_ARG
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.serieseditor.ui.exampanelother.EXAM_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.mainpanel.MAIN_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.topicpanel.navigateToTopicPanel
import com.mshdabiola.topics.navigation.TOPIC_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberExtend(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController = rememberNavController(),
    subjectNavHostController: NavHostController = rememberNavController(),
    examNavHostController: NavHostController = rememberNavController(),
): SeriesEditorAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        mainNavController,
        subjectNavHostController,
        examNavHostController,
    ) {

        Extended(
            navController,
            coroutineScope,
            windowSizeClass,
            mainNavController,
            subjectNavHostController,
            examNavHostController,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberOther(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    pagerState: PagerState = rememberPagerState { 2 },
): SeriesEditorAppState {
    // NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {

        Other(
            navController, coroutineScope, windowSizeClass, pagerState,
        )
    }
}

sealed class SeriesEditorAppState(
    open val navController: NavHostController,
    open val coroutineScope: CoroutineScope,
    open val windowSizeClass: WindowSizeClass,
) {
    abstract val showMainTopBar: Boolean
        @Composable get

    abstract val showPermanentDrawer: Boolean
        @Composable get

    abstract val currentSubjectId: Long
        @Composable get

    abstract fun onSubjectClick(id: Long)
    abstract fun onUpdateSubject(id: Long)
    abstract fun onAddTopic(id: Long)


}

class Extended(
    override val navController: NavHostController,
    override val coroutineScope: CoroutineScope,
    override val windowSizeClass: WindowSizeClass,
    val mainNavController: NavHostController,
    val subjectNavHostController: NavHostController,
    val examNavHostController: NavHostController,

    ) : SeriesEditorAppState(navController, coroutineScope, windowSizeClass) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    override val showMainTopBar: Boolean
        @Composable get() = currentDestination?.route?.contains(MAIN_PANEL_ROUTE) == true

    override val showPermanentDrawer: Boolean
        @Composable get() = currentDestination?.route?.contains(MAIN_PANEL_ROUTE) == true

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

    override fun onAddTopic(id: Long) {
        navController.navigateToTopicPanel(id)
    }
}

class Other @OptIn(ExperimentalFoundationApi::class) constructor(
    override val navController: NavHostController,
    override val coroutineScope: CoroutineScope,
    override val windowSizeClass: WindowSizeClass,
    val pagerState: PagerState,
) : SeriesEditorAppState(navController, coroutineScope, windowSizeClass) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    override val showMainTopBar: Boolean
        @Composable get() = currentDestination?.route?.contains(MAIN_ROUTE) == true

    override val currentSubjectId: Long
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value
            ?.arguments
            ?.getLong(SUBJECT_ARG) ?: -1

    override val showPermanentDrawer: Boolean
        @Composable get() = isMain && windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium


    override fun onSubjectClick(id: Long) {
        navController.navigateToMain(id)
    }

    override fun onUpdateSubject(id: Long) {
        navController.navigateToComposeSubject(id)
    }

    override fun onAddTopic(id: Long) {
        navController.navigateToComposeTopic(id, -1)
    }

    val isMain
        @Composable get() = currentDestination?.route?.contains(MAIN_ROUTE) == true

    val isList
        @Composable
        get() =
            when {
                currentDestination?.route?.contains(MAIN_ROUTE) == true -> true
                currentDestination?.route?.contains(EXAM_PANEL_ROUTE) == true -> true
                currentDestination?.route?.contains(TOPIC_ROUTE) == true -> true
                else -> false
            }

    @OptIn(ExperimentalFoundationApi::class)
    fun onAdd() {
        when {
            navController.currentDestination?.route?.contains(MAIN_ROUTE) == true -> {
                navController.navigateToComposeExamination(-1)
            }

            navController.currentDestination?.route?.contains(EXAM_PANEL_ROUTE) == true -> {
                val exam = navController.currentBackStackEntry?.arguments?.getLong(EXAM_ARG) ?: -1

                if (pagerState.currentPage == 0) {
                    navController.navigateToComposeQuestion(exam, -1)
                } else {
                    navController.navigateToComposeInstruction(exam, -1)
                }

            }

            navController.currentDestination?.route?.contains(TOPIC_ROUTE) == true -> {
                val subject =
                    navController.currentBackStackEntry?.arguments?.getLong(com.mshdabiola.composetopic.navigation.SUBJECT_ARG)
                        ?: -1
                navController.navigateToComposeTopic(subject, -1)
            }

            else -> {}
        }

    }


}

