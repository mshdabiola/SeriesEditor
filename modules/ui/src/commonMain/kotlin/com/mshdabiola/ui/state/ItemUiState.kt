package com.mshdabiola.ui.state

import com.mshdabiola.generalmodel.Type

data class ItemUiState(
    val content: String = "",
    val type: Type = Type.TEXT,
    val isEditMode: Boolean = false,
    val focus: Boolean = false,
)
