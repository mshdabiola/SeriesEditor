package com.mshdabiola.ui.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import com.mshdabiola.generalmodel.Type

@OptIn(ExperimentalFoundationApi::class)
data class ItemUiState(
    val content: TextFieldState = TextFieldState(),
    val type: Type = Type.TEXT,
    val isEditMode: Boolean = false,
    val focus: Boolean = false,
)
sealed class Item(open val type: Type) {
    @OptIn(ExperimentalFoundationApi::class)
    data class Input(
        val content: TextFieldState = TextFieldState(),
        override val type: Type = Type.TEXT,
        val focus: Boolean = false,
    ) : Item(type)

    data class Text(
        val content: String = "",
        override val type: Type = Type.TEXT,
    ) : Item(type)
}
