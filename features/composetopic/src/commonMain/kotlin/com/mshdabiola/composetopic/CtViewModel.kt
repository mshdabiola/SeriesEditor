/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Update
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.generalmodel.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class CtViewModel(
    private val subjectId: Long,
    private val topicId: Long,
    private val topicRepository: ITopicRepository,
) : ViewModel() {

    val topicInput = TextFieldState()

    private val _update = MutableStateFlow(Update.Edit)
    val update = _update.asStateFlow()

    @OptIn(ExperimentalFoundationApi::class)
    val state = TextFieldState()

    init {
        viewModelScope.launch {

            val sub = topicRepository
                .getOne(topicId)
                .first()

            if (sub != null) {

                state.edit {
                    append(sub.title)
                }
            }
        }
    }

    fun addTopic() {
        viewModelScope.launch {
            _update.update {
                Update.Saving
            }

            topicRepository.upsert(
                Topic(
                    id = topicId,
                    subjectId = subjectId,
                    title = state.text.toString(),
                ),
            )
            state.clearText()
            // delay(500)
            _update.update {
                Update.Success
            }
        }
    }

    fun addTopicInput() {
    }
}
