package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T> {
    return this.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
    )
}

@Composable
actual fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T> {
    return this.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,

        initialValue = initialValue,
    )
}

// val ViewModel.viewModelScope: CoroutineScope
//    get() = viewModelScope

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics {
        this.testTagsAsResourceId = true
    }
}
