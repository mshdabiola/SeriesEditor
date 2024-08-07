package com.mshdabiola.composetopic

import com.mshdabiola.seriesmodel.TopicCategory

sealed class CtState {

    data class Loading(val isLoading: Boolean = false) : CtState()
    data class Success(
        val topicId: Long,
        val currentCategoryIndex: Int = 0,
        val categories: List<TopicCategory> = emptyList(),
    ) : CtState()

    data class Error(val exception: Throwable) : CtState()
}

fun CtState.getSuccess(value: (CtState.Success) -> CtState.Success): CtState {
    return if (this is CtState.Success) {
        value(this)
    } else {
        this
    }
}
