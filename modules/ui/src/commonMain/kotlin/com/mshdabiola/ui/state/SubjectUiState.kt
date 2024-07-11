package com.mshdabiola.ui.state

data class SubjectUiState(
    val id: Long = -1,
    val seriesId:Long,
    val name: String,
    val focus: Boolean = false,
)
