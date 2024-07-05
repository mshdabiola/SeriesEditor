package com.mshdabiola.ui.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.mshdabiola.generalmodel.Type

data class ItemUiState @OptIn(ExperimentalFoundationApi::class) constructor(
    val content: TextFieldState = TextFieldState(),
    val type: Type = Type.TEXT,
    val isEditMode: Boolean = false,
    val focus: Boolean = false,
)

sealed class Item(open val type: Type) {
    data class Input @OptIn(ExperimentalFoundationApi::class) constructor(
        val content: TextFieldState = TextFieldState(),
        override val type: Type = Type.TEXT,
        val focus: Boolean = false,
    ) : Item(type)

    data class Text(
        val content: String = "",
       override val type: Type = Type.TEXT,
    ) : Item(type)

}
