package com.mshdabiola.main

import com.mshdabiola.seriesmodel.Series

sealed class MainState {

    data class Loading(val isLoading: Boolean = false) : MainState()
    data class Success(
        val series: List<Series> = emptyList(),
    ) : MainState()

    data class Error(val exception: Throwable) : MainState()
}

fun MainState.getSuccess(value: (MainState.Success) -> MainState.Success): MainState {
    return if (this is MainState.Success) {
        value(this)
    } else {
        this
    }
}
