/*
 *abiola 2022
 */

package com.mshdabiola.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TopicsViewModel(
    subjectId: Long,
    iTopicCategory: ITopicCategory,
    private val topicRepository: ITopicRepository,

) : ViewModel() {

    private val _topics = MutableStateFlow<Result<List<TopicUiState>>>(Result.Loading)
    val topics = _topics.asStateFlow()
//    val topics = iTopicCategory
//        .getTopicBySubject(subjectId)
//        .map { topicList -> topicList.map { it.toUi() } }
//        .asResult()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = Result.Loading,
//        )

    init {

        viewModelScope.launch {
            iTopicCategory
                .getTopicBySubject(subjectId)
                .map { it.map { it.toUi() } }
                .collect {
                    _topics.value = Result.Success(it)

                }

        }
    }

    fun onDelete(id: Long) {
        viewModelScope.launch {
            topicRepository.delete(id)
        }
    }
}
