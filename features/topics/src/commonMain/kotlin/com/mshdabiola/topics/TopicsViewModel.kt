/*
 *abiola 2022
 */

package com.mshdabiola.topics

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.Converter
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicInputUiState
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import com.mshdabiola.ui.toTopic
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TopicsViewModel(
    subjectId: Long,
    private val topicRepository: ITopicRepository,

    ) : ViewModel() {


    val topics =topicRepository
        .getAllBySubject(subjectId)
        .map { topicList -> topicList.map { it.toUi() } }
        .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    fun onDelete(id: Long) {
        viewModelScope.launch {
            topicRepository.delete(id)
        }
    }

}