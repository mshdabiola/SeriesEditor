/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.navOptions
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.SeNavigationDrawerItem
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SeriesEditorGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SeriesEditorTheme
import com.mshdabiola.main.MainTopBarSection
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.serieseditor.MainActivityUiState
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.serieseditor.navigation.SkNavHost
import com.mshdabiola.serieseditor.ui.mainpanel.navigateToMainPanel
import com.mshdabiola.setting.navigation.SETTING_ROUTE
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.template.navigation.navigateToComposeExamination
import com.mshdabiola.ui.CommonBar
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import com.mshdabiola.ui.state.SubjectUiState
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, KoinExperimentalAPI::class)
@Composable
fun SeriesEditorApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val appState = rememberSeriesEditorAppState(
        windowSizeClass = windowSizeClass,
    )
    val shouldShowGradientBackground = false
    val navigator: (String) -> Unit = {
        println("navigation $it seting is $SETTING_ROUTE")

        when (it) {
            MAIN_ROUTE -> {
                appState.navController.navigateToMain(-1, navOptions = navOptions { })
            }

            SETTING_ROUTE -> {
                appState.navController.navigateToSetting()
            }
        }
    }

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    val subjects = viewModel.subjects.collectAsStateWithLifecycleCommon()

    CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
        SeriesEditorTheme(
            darkTheme = darkTheme,
            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
        ) {


            SeriesEditorBackground {
                SeriesEditorGradientBackground(
                    gradientColors = if (shouldShowGradientBackground) {
                        LocalGradientColors.current
                    } else {
                        GradientColors()
                    },
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }

                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentDrawerSheet(

                            ) {
                                val currentSubjectId =
                                    (appState.largeScreen as? Screen.Main)?.subjectId
                                NavigationSheet(
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 8.dp),
                                    subjects = subjects.value,

                                    addSubject = {},
                                    onSubjectClick = appState.navController::navigateToMainPanel,
                                    checkIfSelected = { currentSubjectId == it },
                                )

                            }

                        },
                    ) {
                        Scaffold(
                            modifier = Modifier.semanticsCommon {},
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            contentWindowInsets = WindowInsets(0, 0, 0, 0),
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            bottomBar = {
                                if (appState.shouldShowBottomBar) {
                                    CommonBar(
                                        currentNavigation = appState.currentDestination?.route
                                            ?: "",
                                    ) { navigator(it) }
                                }
                            },
                            topBar = {
                                if (appState.largeScreen is Screen.Main) {
                                    MainTopBarSection(
                                        navigateToSetting = appState.navController::navigateToSetting,
                                        currentSubjectId = (appState.largeScreen as? Screen.Main)?.subjectId
                                            ?: -1,
                                    )
                                }
                            }

                            ) { padding ->

                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                                    .consumeWindowInsets(padding)
                                    .windowInsetsPadding(
                                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                                    ),
                            ) {
                                SkNavHost(
//                                                    modifier = Modifier.weight(0.7f),
                                    appState = appState,
                                    onShowSnackbar = { message, action ->
                                        snackbarHostState.showSnackbar(
                                            message = message,
                                            actionLabel = action,
                                            duration = SnackbarDuration.Short,
                                        ) == SnackbarResult.ActionPerformed
                                    },
                                )
//                                            }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationSheet(
    modifier: Modifier,
    subjects: List<SubjectUiState>,
    addSubject: (() -> Unit)? = null,
    onSubjectClick: (Long) -> Unit = {},
    checkIfSelected: (Long) -> Boolean = { false },
) {

    LazyColumn(modifier = modifier) {
        item {
            Text("Series Editor ")
        }
        item {
            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(modifier = Modifier.weight(1f), text = "Subject")
                if (addSubject != null) {
                    IconButton(onClick = addSubject) {
                        Icon(Icons.Default.Add, "add")
                    }
                }

            }
        }
        item {
            SeNavigationDrawerItem(
                selected = checkIfSelected(-1),
                label = "All Subject",
                onClick = { onSubjectClick(-1) },
            )
        }
        items(subjects, key = { it.id }) {
            SeNavigationDrawerItem(
                selected = checkIfSelected(it.id),
                label = it.name,
                onClick = { onSubjectClick(it.id) },
            )
        }
    }

}

@Composable
private fun chooseTheme(
    uiState: MainActivityUiState,
): ThemeBrand = when (uiState) {
    MainActivityUiState.Loading -> ThemeBrand.DEFAULT
    is MainActivityUiState.Success -> uiState.userData.themeBrand
}

@Composable
private fun shouldUseAndroidTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> when (uiState.userData.themeBrand) {
        ThemeBrand.DEFAULT -> false
        ThemeBrand.GREEN -> true
    }
}

@Composable
private fun chooseContrast(
    uiState: MainActivityUiState,
): Contrast = when (uiState) {
    MainActivityUiState.Loading -> Contrast.Normal
    is MainActivityUiState.Success -> uiState.userData.contrast
}

@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
}

@Composable
fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }
