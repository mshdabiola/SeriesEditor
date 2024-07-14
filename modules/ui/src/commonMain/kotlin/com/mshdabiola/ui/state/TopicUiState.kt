package com.mshdabiola.ui.state

import com.mshdabiola.generalmodel.TopicCategory

data class TopicUiState(
    val id: Long = -1,
    val topicCategory: TopicCategory,
    val name: String,
    val focus: Boolean = false,
)
