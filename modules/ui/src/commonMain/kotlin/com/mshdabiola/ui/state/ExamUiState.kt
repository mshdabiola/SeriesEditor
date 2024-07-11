package com.mshdabiola.ui.state

data class ExamUiState(
    val id: Long = -1,
    val year: Long = 2012,
    val isObjectiveOnly: Boolean = false,
    val duration: Long = 15,
    val subject: SubjectUiState = SubjectUiState(name = "", seriesId = 4),
    val isSelected: Boolean = false,
)
