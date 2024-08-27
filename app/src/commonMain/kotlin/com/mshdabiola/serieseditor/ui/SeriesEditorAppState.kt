/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.mshdabiola.composeexam.navigation.navigateToComposeExamination
import com.mshdabiola.composeinstruction.navigation.navigateToComposeInstruction
import com.mshdabiola.composequestion.navigation.navigateToComposeQuestion
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.composetopic.navigation.navigateToComposeTopic
import com.mshdabiola.login.navigation.LOGIN_ROUTE
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.serieseditor.ui.examItemspanel.EXAM_ITEM_ARG
import com.mshdabiola.serieseditor.ui.examItemspanel.EXAM_ITEM_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.subjectitemspanel.SUBJECT_ITEM_PANEL_ROUTE
import com.mshdabiola.serieseditor.ui.subjectpanel.SUBJECT_PANEL_ROUTE
import com.mshdabiola.subjects.navigation.SERIES_ID
import com.mshdabiola.topics.navigation.TOPIC_ROUTE
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    examPagerState: PagerState = rememberPagerState { 2 },
    subjectPagerState: PagerState = rememberPagerState { 2 },

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
            examPagerState,
            subjectPagerState,
        )
    }
}

class SeriesEditorAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    val examPagerState: PagerState,
    val subjectPagerState: PagerState,
) {

    val isSmallScreen: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val showMainTopBar: Boolean
        @Composable get() = currentDestination?.route?.contains(MAIN_ROUTE) == true ||
            currentDestination?.route?.contains("setting") == true

    val topbarTitle: String
        @Composable get() = when {
            currentDestination?.route?.contains(SUBJECT_PANEL_ROUTE) == true -> "Subject"
            currentDestination?.route?.contains(SUBJECT_ITEM_PANEL_ROUTE) == true -> "Subject Item"
            currentDestination?.route?.contains(EXAM_ITEM_PANEL_ROUTE) == true -> "Examination"
            else -> "Compose"
        }

    val hideTopBar: Boolean
        @Composable get() = currentDestination?.route?.contains(LOGIN_ROUTE) == true

    val isList
        @Composable
        get() =
            when {
                currentDestination?.route?.contains(SUBJECT_PANEL_ROUTE) == true -> true
                currentDestination?.route?.contains(SUBJECT_ITEM_PANEL_ROUTE) == true -> true
                currentDestination?.route?.contains(EXAM_ITEM_PANEL_ROUTE) == true -> true
                else -> false
            }

    val fabText: String
        @Composable
        get() =
            when {
                currentDestination?.route?.contains(SUBJECT_PANEL_ROUTE) == true -> "Add Subject"
                currentDestination?.route?.contains(EXAM_ITEM_PANEL_ROUTE) == true -> {
                    if (examPagerState.currentPage == 0) {
                        "Add Question"
                    } else {
                        "Add Instruction"
                    }
                }

                currentDestination?.route?.contains(SUBJECT_ITEM_PANEL_ROUTE) == true -> {
                    if (subjectPagerState.currentPage == 0) {
                        "Add Examination"
                    } else {
                        "Add Topic"
                    }
                }

                else -> "Add"
            }

    fun onNavigateToCompose(id: Long = -1) {
        when {
            navController.currentDestination?.route?.contains(SUBJECT_PANEL_ROUTE) == true -> {
                val seriesId =
                    navController.currentBackStackEntry?.arguments?.getLong(SERIES_ID)
                        ?: -1
                navController.navigateToComposeSubject(seriesId, id)
            }

            navController.currentDestination?.route?.contains(EXAM_ITEM_PANEL_ROUTE) == true -> {
                val exam = navController.currentBackStackEntry?.arguments?.getLong(EXAM_ITEM_ARG) ?: -1

                if (examPagerState.currentPage == 0) {
                    navController.navigateToComposeQuestion(exam, id)
                } else {
                    navController.navigateToComposeInstruction(exam, id)
                }
            }

            navController.currentDestination?.route?.contains(SUBJECT_ITEM_PANEL_ROUTE) == true -> {
                val subjectId =
                    navController.currentBackStackEntry?.arguments?.getLong(com.mshdabiola.serieseditor.ui.subjectitemspanel.SUBJECT_ARG)
                        ?: -1

                if (subjectPagerState.currentPage == 0) {
                    navController.navigateToComposeExamination(subjectId, id)
                } else {
                    navController.navigateToComposeTopic(subjectId, id)
                }
            }

            navController.currentDestination?.route?.contains(TOPIC_ROUTE) == true -> {
                val subject =
                    navController.currentBackStackEntry?.arguments?.getLong(com.mshdabiola.composetopic.navigation.SUBJECT_ARG)
                        ?: -1
                navController.navigateToComposeTopic(subject, id)
            }

            else -> {}
        }
    }
}
