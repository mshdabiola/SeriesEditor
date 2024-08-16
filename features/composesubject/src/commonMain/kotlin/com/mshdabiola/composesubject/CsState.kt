package com.mshdabiola.composesubject

sealed class CsState {

    data class Loading(val isLoading: Boolean = false) : CsState()
    data class Success(
        val id: Long,
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
