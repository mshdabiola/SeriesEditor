package com.mshdabiola.composeexam

sealed class CeState {

    data class Loading(val isLoading: Boolean = false) : CeState()
    data class Success(
        val isUpdate: Boolean = false,
    ) : CeState()
    data class Error(val exception: Throwable) : CeState()
}

fun CeState.getSuccess(value: (CeState.Success) -> CeState.Success): CeState {
    return if (this is CeState.Success) {
        value(this)
    } else {
        this
    }
}
