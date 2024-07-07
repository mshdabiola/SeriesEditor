/*
 *abiola 2022
 */

package com.mshdabiola.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TopicsViewModel(
    subjectId: Long,
    private val topicRepository: ITopicRepository,

) : ViewModel() {

    val topics = topicRepository
        .getAllBySubject(subjectId)
        .map { topicList -> topicList.map { it.toUi() } }
        .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading,
        )

    fun onDelete(id: Long) {
        viewModelScope.launch {
            topicRepository.delete(id)
        }
    }
}
