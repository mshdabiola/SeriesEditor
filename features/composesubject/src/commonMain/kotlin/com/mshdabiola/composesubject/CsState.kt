package com.mshdabiola.composesubject

import com.mshdabiola.generalmodel.Series

sealed class CsState {

    data class Loading(val isLoading: Boolean = false) : CsState()
    data class Success(
        val currentSeries: Long,
        val series: List<Series> = emptyList(),
    ) : CsState()

    data class Error(val exception: Throwable) : CsState()
}

fun CsState.getSuccess(value: (CsState.Success) -> CsState.Success): CsState {
    return if (this is CsState.Success) {
        value(this)
    } else {
        this
    }
}
