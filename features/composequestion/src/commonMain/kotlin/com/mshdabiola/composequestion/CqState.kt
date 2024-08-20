package com.mshdabiola.composequestion

import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed class CqState {

    data class Loading(val isLoading: Boolean = false) : CqState()
    data class Success(

        val questionUiState: QuestionUiState,
        val topics: ImmutableList<TopicUiState> = emptyList<TopicUiState>().toImmutableList(),
        val instructs: ImmutableList<InstructionUiState> = emptyList<InstructionUiState>().toImmutableList(),

    ) : CqState()

    data class Error(val exception: Throwable) : CqState()
}

fun CqState.getSuccess(value: (CqState.Success) -> CqState.Success): CqState {
    return if (this is CqState.Success) {
        value(this)
    } else {
        this
    }
}
