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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SeNavigationDrawerItem
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SeriesEditorGradientBackground
import com.mshdabiola.designsystem.component.SeriesEditorTopAppBar
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SeriesEditorTheme
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.serieseditor.MainActivityUiState
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.serieseditor.navigation.ExtendNavHost
import com.mshdabiola.serieseditor.navigation.OtherNavHost
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SeriesEditorApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val appState =
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Expanded -> rememberExtend(windowSizeClass)
            else -> rememberOther(windowSizeClass)
        }
    val shouldShowGradientBackground = false

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    val subjects = viewModel.subjects.collectAsStateWithLifecycleCommon()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutincope = rememberCoroutineScope()

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
                    val currentSubjectId = appState.currentSubjectId

                    PermanentNavigationDrawer(
                        drawerContent = {
                            if (appState.showPermanentDrawer) {
                                PermanentDrawerSheet(
                                    modifier = Modifier.widthIn(max = 300.dp),
                                ) {
                                    NavigationSheet(
                                        modifier = Modifier
                                            .padding(top = 16.dp, start = 16.dp, end = 8.dp),
                                        subjects = subjects.value,

                                        addSubject = {},
                                        onSubjectClick = appState::onSubjectClick,
                                        checkIfSelected = { currentSubjectId == it },
                                    )


                                }
                            }
                        },
                    ) {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            drawerContent = {
                                if (!appState.showPermanentDrawer) {
                                    ModalDrawerSheet(
                                        modifier = Modifier.widthIn(max = 300.dp),
                                    ) {
                                        NavigationSheet(
                                            modifier = Modifier
                                                .padding(top = 16.dp, start = 16.dp, end = 8.dp),
                                            subjects = subjects.value,

                                            addSubject = {},
                                            onSubjectClick = {
                                                appState.onSubjectClick(it)
                                                coroutincope.launch { drawerState.close()}
                                            },
                                            checkIfSelected = { currentSubjectId == it },
                                        )
                                    }
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
//                                if (appState.shouldShowBottomBar) {
//                                    CommonBar(
//                                        currentNavigation = appState.currentDestination?.route
//                                            ?: "",
//                                    ) { navigator(it) }
//                                }
                                },
                                topBar = {
                                    if (appState.showMainTopBar) {
                                        MainTopBarSection(
                                            navigateToSetting = appState.navController::navigateToSetting,
                                            subjectId = currentSubjectId,
                                            updateSubject = appState::onUpdateSubject,
                                            onNavigationClick = if (!appState.showPermanentDrawer) {
                                                { coroutincope.launch { drawerState.open() } }
                                            } else null,
                                        )
                                    }else{
                                        DetailTopAppBar(
                                           onNavigationClick = appState.navController::popBackStack
                                        )

                                    }
                                },

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
                                    when (appState) {
                                        is Extended -> {
                                            ExtendNavHost(
                                                appState = appState,
                                                onShowSnackbar = { message, action ->
                                                    snackbarHostState.showSnackbar(
                                                        message = message,
                                                        actionLabel = action,
                                                        duration = SnackbarDuration.Short,
                                                    ) == SnackbarResult.ActionPerformed
                                                },
                                            )
                                        }

                                        is Other -> {
                                            OtherNavHost(
                                                appState = appState,
                                                onShowSnackbar = { message, action ->
                                                    snackbarHostState.showSnackbar(
                                                        message = message,
                                                        actionLabel = action,
                                                        duration = SnackbarDuration.Short,
                                                    ) == SnackbarResult.ActionPerformed
                                                },
                                            )
                                        }
                                    }

                                }
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
