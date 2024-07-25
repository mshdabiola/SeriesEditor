package com.mshdabiola.composequestion

import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
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
        val fillIt: Boolean = false,

        ) : CqState()

    data class Error(val exception: Throwable) : CqState()

}