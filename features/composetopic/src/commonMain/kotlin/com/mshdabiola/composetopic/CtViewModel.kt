/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.mshdabiola.data.model.Update
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.TopicCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class CtViewModel(
    private val subjectId: Long,
    private val topicId: Long,
    private val topicRepository: ITopicRepository,
    private val topicCategory: ITopicCategory,
    private val logger: Logger,
) : ViewModel() {

    val topicInput = TextFieldState()

    val categoryState = TextFieldState()

    val categories = topicCategory
        .getAll()
        .map { it.filter { it.subjectId == subjectId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentCategoryId = mutableStateOf(1L)

    private val _update = MutableStateFlow(Update.Edit)
    val update = _update.asStateFlow()

    @OptIn(ExperimentalFoundationApi::class)
    val state = TextFieldState()

    init {
        viewModelScope.launch {
            val list = topicCategory
                .getAll()
                .map { categories1 -> categories1.filter { it.subjectId == subjectId } }
                .first()
            if (list.isEmpty()) {
                topicCategory.upsert(TopicCategory(-1, "UnCategorized", subjectId))
            }
        }
        viewModelScope.launch {

            val sub = topicRepository
                .getOneWithCategory(topicId)
                .first()

            if (sub != null) {

                state.edit {
                    append(sub.title)
                }
                kotlinx.coroutines.delay(1500)
                currentCategoryId.value = sub.topicCategory.id
                if (sub.topicCategory.id > 1) {

                    categoryState.edit {
                        append(sub.topicCategory.name)
                    }
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
                    categoryId = currentCategoryId.value,
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

    fun onCurrentSeriesChange(id: Long) {
        currentCategoryId.value = id

        categoryState.clearText()

        if (id > 1) {
            categoryState.edit {
                append(categories.value.find { it.id == id }?.name)
            }
        }
    }

    fun addCategory() {
        viewModelScope.launch {
            val id = if (currentCategoryId.value == 1L) -1 else currentCategoryId.value

            topicCategory.upsert(TopicCategory(-1, "Testing", subjectId))

            val newId = topicCategory.upsert(
                TopicCategory(
                    id = id,
                    subjectId = subjectId,
                    name = categoryState.text.toString(),
                ),
            )
            if (newId > 0) {
                currentCategoryId.value = newId
            }
        }
    }

    fun onDeleteCategory() {
        viewModelScope.launch {
            topicCategory.delete(currentCategoryId.value)
            categoryState.clearText()
            currentCategoryId.value = 1
        }
    }

    fun addTopicInput() {
    }
}
