package com.mshdabiola.composeexam

import com.mshdabiola.ui.state.SubjectUiState

sealed class CeState {

    data class Loading(val isLoading: Boolean = false) : CeState()
    data class Success(
        val isUpdate: Boolean = false,
        val subjects: List<SubjectUiState> = emptyList(),
    ) : CeState()
    data class Error(val exception: Throwable) : CeState()
}
