/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.composesubject.navigation.navigateToComposeSubject
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SeNavigationDrawerItem
import com.mshdabiola.designsystem.component.SeriesEditorBackground
import com.mshdabiola.designsystem.component.SeriesEditorGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SeriesEditorTheme
import com.mshdabiola.generalmodel.User
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.serieseditor.MainActivityUiState
import com.mshdabiola.serieseditor.MainAppViewModel
import com.mshdabiola.serieseditor.MainState
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

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
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
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    val subjects = viewModel.subjects.collectAsStateWithLifecycleCommon()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutine = rememberCoroutineScope()
    val open: () -> Unit = { coroutine.launch { drawerState.open() } }

    val mainState = viewModel.mainState.collectAsStateWithLifecycle()
    val currentSubjectId = appState.currentSubjectId

    LaunchedEffect(mainState.value) {
        if (mainState.value is MainState.Success && (mainState.value as MainState.Success).message.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                (mainState.value as MainState.Success).message,
                duration = SnackbarDuration.Long,
            )
        }
    }

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
                    AnimatedContent(mainState.value) {
                        when (it) {
                            is MainState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is MainState.Success -> {
                                PermanentNavigationDrawer(
                                    drawerContent = {
                                        if (appState.showPermanentDrawer) {
                                            PermanentDrawerSheet(
                                                modifier = Modifier.widthIn(max = 300.dp),
                                            ) {
                                                NavigationSheet(
                                                    modifier = Modifier
                                                        .padding(
                                                            top = 16.dp,
                                                            start = 16.dp,
                                                            end = 8.dp,
                                                        ),
                                                    subjects = subjects.value,

                                                    addSubject = null,
                                                    onSubjectClick = appState::onSubjectClick,
                                                    checkIfSelected = { currentSubjectId == it },
                                                    user = it.user,
                                                )
                                            }
                                        }
                                    },
                                ) {
                                    ModalNavigationDrawer(
                                        drawerState = drawerState,
                                        drawerContent = {
                                            if (appState.showDrawer) {
                                                LaunchedEffect(Unit) {
                                                    drawerState.close()
                                                }
                                                ModalDrawerSheet(
                                                    modifier = Modifier.widthIn(max = 300.dp),
                                                ) {
                                                    NavigationSheet(
                                                        modifier = Modifier
                                                            .padding(
                                                                top = 16.dp,
                                                                start = 16.dp,
                                                                end = 8.dp,
                                                            ),
                                                        subjects = subjects.value,

                                                        addSubject = {
                                                            appState.navController.navigateToComposeSubject(
                                                                -1,
                                                            )
                                                        },
                                                        onSubjectClick = {
                                                            appState.onSubjectClick(it)
                                                            coroutine.launch { drawerState.close() }
                                                        },
                                                        checkIfSelected = { currentSubjectId == it },
                                                        user = it.user,
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
                                                if (appState is Other) {
                                                    MainBottomBarSection(
                                                        modifier = Modifier,
                                                        onNavigationClick = if (appState.isMain && !appState.showPermanentDrawer) {
                                                            open
                                                        } else {
                                                            null
                                                        },
                                                        onAddTopic = if (currentSubjectId > 0) {
                                                            { appState.onAddTopic(currentSubjectId) }
                                                        } else {
                                                            null
                                                        },
                                                        subjectId = currentSubjectId,
                                                        appState = appState,
                                                    )
                                                }
//                                if (appState.shouldShowBottomBar) {
//                                    CommonBar(
//                                        currentNavigation = appState.currentDestination?.route
//                                            ?: "",
//                                    ) { navigator(it) }
//                                }
                                            },
                                            topBar = {
                                                if (appState is Extended) {
                                                    if (appState.showMainTopBar) {
                                                        MainTopBarSection(
                                                            navigateToSetting = appState.navController::navigateToSetting,
                                                            subjectId = currentSubjectId,
                                                            updateSubject = appState::onUpdateSubject,
                                                            onNavigationClick = if (!appState.showPermanentDrawer) {
                                                                open
                                                            } else {
                                                                null
                                                            },
                                                            onAddTopic = if (currentSubjectId > 0) {
                                                                {
                                                                    appState.onAddTopic(
                                                                        currentSubjectId,
                                                                    )
                                                                }
                                                            } else {
                                                                null
                                                            },
                                                        )
                                                    } else {
                                                        DetailTopAppBar(
                                                            onNavigationClick = appState.navController::popBackStack,
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
