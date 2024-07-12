package com.mshdabiola.ui.state

data class SubjectUiState(
    val id: Long = -1,
    val seriesLabel: String,
    val name: String,
    val focus: Boolean = false,
)
