/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mshdabiola.ui.Waiting
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun MainRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    val viewModel: MainViewModel = koinViewModel()

    val update = viewModel.mainState.collectAsStateWithLifecycleCommon()

    MainScreen(
        modifier = modifier,
        subjectState = viewModel.classState,
        mainState = update.value
    )
}

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    mainState: MainState,
) {
    AnimatedContent(
        targetState = mainState,
        modifier = modifier
            .testTag("main:screen"),

    ) {
        when (it) {
            is MainState.Success -> MainContent(
                modifier = modifier,
                subjectState = subjectState,
                mainState = it,
            )

            is MainState.Loading -> {
                Waiting()
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    subjectState: TextFieldState,
    mainState: MainState.Success,
) {

}
