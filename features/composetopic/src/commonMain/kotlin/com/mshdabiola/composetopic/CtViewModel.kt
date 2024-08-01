/*
 *abiola 2022
 */

package com.mshdabiola.composetopic

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.mshdabiola.data.Converter
import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.TopicCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CtViewModel(
    private val subjectId: Long,
    private val topicId: Long,
    private val topicRepository: ITopicRepository,
    private val topicCategory: ITopicCategory,
    private val logger: Logger,
) : ViewModel() {

    val topicInput = TextFieldState()

    val categoryState = TextFieldState()

    private val _ctState = MutableStateFlow<CtState>(CtState.Loading())
    val ctState = _ctState.asStateFlow()

    val state = TextFieldState()

    private val defaultName = "UnCategorized"

    init {
        viewModelScope.launch {
            val list = topicCategory
                .getAll()
                .map { categories1 -> categories1.filter { it.subjectId == subjectId } }
                .first()
            if (list.isEmpty()) {
                topicCategory.upsert(TopicCategory(-1, defaultName, subjectId))
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

                if (sub.topicCategory.id > 1) {

                    categoryState.edit {
                        append(sub.topicCategory.name)
                    }
                }
            }
            _ctState.update {
                CtState.Success(
                    topicId = topicId,
                )
            }

            topicCategory
                .getAll()
                .map { it.filter { it.subjectId == subjectId } }
                .collectLatest { list ->

                    _ctState.update { state1 ->
                        state1.getSuccess {
                            it.copy(categories = list)
                        }
                    }
                }
        }
    }

    fun addTopic() {
        viewModelScope.launch {
            val success = (ctState.value as CtState.Success)
            val topic = Topic(
                id = topicId,
                categoryId = success.categories[success.currentCategoryIndex].id,
                title = state.text.toString(),
            )
            _ctState.update {
                CtState.Loading()
            }

            topicRepository.upsert(
                topic = topic,
            )
            state.clearText()
            // delay(500)
            _ctState.update {
                CtState.Loading(true)
            }
        }
    }

    fun onCurrentSeriesChange(index: Int) {
        _ctState.update { state1 ->
            state1.getSuccess {
                it.copy(currentCategoryIndex = index)
            }
        }
        logger.d { "categories id $index" }
        val name = (ctState.value as CtState.Success).categories.getOrNull(index)?.name

        categoryState.clearText()
        if (name.equals(defaultName).not()) {
            categoryState.edit {
                append(name)
            }
        }
    }

    fun addCategory() {
        viewModelScope.launch {
            val success = (ctState.value as CtState.Success)

            val index = success.currentCategoryIndex

            val id = success.categories[success.currentCategoryIndex].id
            val size = success.categories.size

            topicCategory.upsert(
                TopicCategory(
                    id = if (index == 0)-1 else id,
                    subjectId = subjectId,
                    name = categoryState.text.toString(),
                ),
            )
            if (index > 0) {
                _ctState.update {
                    it.getSuccess {
                        it.copy(currentCategoryIndex = index)
                    }
                }
            } else {
                _ctState.update {
                    it.getSuccess {
                        it.copy(currentCategoryIndex = size)
                    }
                }
            }
        }
    }

    fun onDeleteCategory() {
        viewModelScope.launch {
            val success = (ctState.value as CtState.Success)

            topicCategory.delete(success.categories[success.currentCategoryIndex].id)
            categoryState.clearText()
            _ctState.update {
                it.getSuccess {
                    it.copy(currentCategoryIndex = 0)
                }
            }
        }
    }

    fun addTopicInput() {
        viewModelScope.launch {
            val success = (ctState.value as CtState.Success)

            _ctState.update {
                CtState.Loading()
            }

            val topics = Converter().textToTopic(
                topicInput.text.toString(),
                categoryId = success.categories[success.currentCategoryIndex].id,

            )

            topics.forEach {
                topicRepository.upsert(
                    topic = it,
                )
            }

            topicInput.clearText()
            // delay(500)
            _ctState.update {
                CtState.Loading(true)
            }
        }
    }
}
