package com.mshdabiola.main

sealed class ExportState {

    data class Loading(val isLoading: Boolean = false) : ExportState()
    data class Success(
        val exams: List<ExamState> = emptyList(),
    ) : ExportState()

    data class Error(val exception: Throwable) : ExportState()
}

fun ExportState.getSuccess(value: (ExportState.Success) -> ExportState.Success): ExportState {
    return if (this is ExportState.Success) {
        value(this)
    } else {
        this
    }
}
