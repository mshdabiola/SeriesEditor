package com.mshdabiola.serieseditor.ui

//
//@OptIn(KoinExperimentalAPI::class)
//@Composable
//fun MainTopBarSection(
//    modifier: Modifier = Modifier,
//    navigateToSetting: () -> Unit,
//    appState: SeriesEditorAppState,
//) {
//    val viewModel: MainAppViewModel = koinViewModel()
//
//    var showDeleteDialog by remember { mutableStateOf(false) }
//    var showDialog by remember { mutableStateOf(false) }
//    val isSelect = viewModel.isSelectMode.collectAsStateWithLifecycleCommon()
//
//    var path by remember { mutableStateOf<String?>(null) }
//    var hasPermission by remember { mutableStateOf(false) }
//    var showPermissionDialog by remember { mutableStateOf(false) }
//    HasWrittenPermission {
//        hasPermission = it
//    }
//
//    GetFilePath {
//        path = it?.absolutePath
//    }
//
//    MainTopBar(
//        modifier = modifier,
//        isSelectMode = isSelect.value,
//        currentSubjectId = appState.currentSubjectId,
//        selectAll = viewModel::selectAll,
//        deselectAll = viewModel::deselectAll,
//        navigateToSetting = navigateToSetting,
//        showExportDialog = {
//            if (hasPermission) {
//                showDialog = true
//            } else {
//                showPermissionDialog = true
//            }
//        },
//        exportWord = { viewModel.onExportWord(path!!) },
//        toggleSelectMode = viewModel::toggleSelectMode,
//        showDeleteDialog = { showDeleteDialog = true },
//        isMain = appState.isMain,
//        isExam = appState.isExam,
//        onNavigationClick = appState.navController::popBackStack,
//    )
//
//    if (showPermissionDialog) {
//        PermissionDialog(
//            onDismiss = { showPermissionDialog = false },
//            onFile = {
//                path = it?.absolutePath
//                if (it != null) {
//                    hasPermission = true
//                }
//                showPermissionDialog = false
//            },
//        )
//    }
//    MainExportDialog(
//        show = showDialog,
//        export = { viewModel.onExport(path!!, it) },
//        onClose = { showDialog = false },
//    )
//
//    DeleteDialog(
//        show = showDeleteDialog,
//        onDismiss = {
//            showDeleteDialog = false
//        },
//        onDelete = {
//            viewModel.deleteSelected()
//            showDeleteDialog = false
//        },
//    )
//}
//
//@OptIn(KoinExperimentalAPI::class)
//@Composable
//fun MainBottomBarSection(
//    modifier: Modifier = Modifier,
//    appState: Other,
//    subjectId: Long,
//    fabText: String,
//
//) {
//    val viewModel: MainAppViewModel = koinViewModel()
//
//    var showDeleteDialog by remember { mutableStateOf(false) }
//    var showDialog by remember { mutableStateOf(false) }
//    val isSelect = viewModel.isSelectMode.collectAsStateWithLifecycleCommon()
//
//    var path by remember { mutableStateOf<String?>(null) }
//    var hasPermission by remember { mutableStateOf(false) }
//    var showPermissionDialog by remember { mutableStateOf(false) }
//    HasWrittenPermission {
//        hasPermission = it
//    }
//
//    GetFilePath {
//        path = it?.absolutePath
//    }
//
//    SeBottonAppBar(
//        modifier = modifier,
//        isSelectMode = isSelect.value,
//        fabText = fabText,
//        currentSubjectId = subjectId,
//        selectAll = viewModel::selectAll,
//        deselectAll = viewModel::deselectAll,
//        showExportDialog = {
//            if (hasPermission) {
//                showDialog = true
//            } else {
//                showPermissionDialog = true
//            }
//        },
//        toggleSelectMode = viewModel::toggleSelectMode,
//        showDeleteDialog = { showDeleteDialog = true },
//        onFabClick =
//        if (appState.isList) {
//            {
//                appState.onAdd()
//            }
//        } else {
//            null
//        },
//        onSettingsClick = appState.navController::navigateToSetting,
//        exportWord = { viewModel.onExportWord(path!!) },
//        onBackClick = appState.navController::popBackStack,
//        isMain = appState.isMain,
//        isExam = appState.isExam,
//    )
//    if (showPermissionDialog) {
//        PermissionDialog(
//            onDismiss = { showPermissionDialog = false },
//            onFile = {
//                path = it?.absolutePath
//                if (it != null) {
//                    hasPermission = true
//                }
//                showPermissionDialog = false
//            },
//        )
//    }
//    MainExportDialog(
//        show = showDialog,
//        export = { viewModel.onExport(path!!, it) },
//        onClose = { showDialog = false },
//    )
//
//    DeleteDialog(
//        show = showDeleteDialog,
//        onDismiss = {
//            showDeleteDialog = false
//        },
//        onDelete = {
//            viewModel.deleteSelected()
//            showDeleteDialog = false
//        },
//    )
//}
//
//sealed class DeleteState {
//    data object All : DeleteState()
//    data class Id(val id: Long) : DeleteState()
//}
