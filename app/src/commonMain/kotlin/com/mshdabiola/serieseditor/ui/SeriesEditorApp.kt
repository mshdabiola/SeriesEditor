/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.SeNavigationDrawerItem
import com.mshdabiola.designsystem.component.SerMainTopAppBar
import com.mshdabiola.designsystem.component.SerSubTopAppBar
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SeriesEditorGradientBackground
import com.mshdabiola.designsystem.string.appName
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SeriesEditorTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.serieseditor.MainActivityUiState
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.serieseditor.navigation.OtherNavHost
import com.mshdabiola.serieslatex.LoadTex
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import com.mshdabiola.ui.state.SubjectUiState
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
)
@Composable
fun SeriesEditorApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val appState = rememberAppState(windowSizeClass)

    val shouldShowGradientBackground = false
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    val mainState = viewModel.mainState.collectAsStateWithLifecycle()

    LoadTex()

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
                    Scaffold(
                        modifier = Modifier.semanticsCommon {},
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        floatingActionButton = {
                            if (appState.isSmallScreen) {
                                if (appState.isList) {
                                    ExtendedFloatingActionButton(
                                        modifier = Modifier.testTag("add").navigationBarsPadding(),
                                        onClick = appState::onAdd,
                                    ) {
                                        Icon(Icons.Outlined.Add, "add")
                                        Text(appState.fabText)
                                    }
                                }
                            }
                        },

                        topBar = {
                            if (appState.hideTopBar.not()) {
                                if (appState.showMainTopBar) {
                                    SerMainTopAppBar(
                                        titleRes = appName,
                                        onProfile = { },
                                        onNavigationClick = { appState.navController.navigateToSetting() },

                                    )
                                } else {
                                    SerSubTopAppBar(
                                        title = appState.topbarTitle,
                                        onBack = appState.navController::popBackStack,
                                    )
                                }
                            }
                        },

                    ) { padding ->

                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .consumeWindowInsets(padding)
                                .windowInsetsPadding(
                                    WindowInsets.safeDrawing.only(
                                        WindowInsetsSides.Horizontal,
                                    ),
                                ),
                        ) {
                            OtherNavHost(
                                appState = appState,
                                onShowSnackbar = { message, action ->
                                    snackbarHostState.showSnackbar(
                                        message = message,
                                        actionLabel = action,
                                        duration = SnackbarDuration.Short,
                                    ) == SnackbarResult.ActionPerformed
                                },
                                userId = viewModel.mainState.value.userId,

                            )
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
    user: User?,
    subjects: List<SubjectUiState>,
    addSubject: (() -> Unit)? = null,
    onSubjectClick: (Long) -> Unit = {},
    checkIfSelected: (Long) -> Boolean = { false },
) {
    LazyColumn(modifier = modifier) {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person",
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        "${user?.name}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        "${user?.type?.name?.lowercase()?.replaceFirstChar { it.uppercaseChar() }}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
//            Text(
//                text = "Series Editor ",
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.titleLarge,
//            )
        }

        item {
            Spacer(Modifier.height(16.dp))
            if (addSubject != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ElevatedButton(onClick = addSubject) {
                        Text("Add Subject")
                    }
                }
            }
        }

        item {
            Spacer(Modifier.height(16.dp))
            Text(text = "Subjects")
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
                series = it.seriesLabel,
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
