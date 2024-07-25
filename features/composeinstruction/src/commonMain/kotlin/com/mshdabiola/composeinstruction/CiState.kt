package com.mshdabiola.composeinstruction

import androidx.compose.foundation.text.input.TextFieldState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList

sealed class CiState {

    data class Loading(val isLoading: Boolean=false) : CiState()
    data class Success(
        val id: Long = -1,
        val examId: Long,
        val content: ImmutableList<ItemUiState>,

        ) : CiState()
    data class Error(val exception: Throwable) : CiState()

}