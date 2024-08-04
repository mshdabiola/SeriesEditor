package com.mshdabiola.serieseditor

import com.mshdabiola.generalmodel.User

sealed class MainState {

    data object Loading : MainState()
    data class Success(
        val user: User,
        val message: String = "",
    ) : MainState()
}

fun MainState.getSuccess(value: (MainState.Success) -> MainState.Success): MainState {
    return if (this is MainState.Success) {
        value(this)
    } else {
        this
    }
}
