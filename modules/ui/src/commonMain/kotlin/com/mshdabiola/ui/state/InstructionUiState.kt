package com.mshdabiola.ui.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import kotlinx.collections.immutable.ImmutableList
@OptIn(ExperimentalFoundationApi::class)
data class InstructionUiState(
    val id: Long = -1,
    val examId: Long,
    val title: TextFieldState,
    val content: ImmutableList<ItemUiState>,
)
