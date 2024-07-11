/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Update
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.generalmodel.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class ComposeSubjectViewModel(
    private val seriesId: Long,
    private val subjectId: Long,
    private val subjectRepository: ISubjectRepository,
) : ViewModel() {

    @OptIn(ExperimentalFoundationApi::class)
    val state = TextFieldState()

    private val _update = MutableStateFlow(Update.Edit)
    val update = _update.asStateFlow()

    init {
        viewModelScope.launch {
            if (subjectId > 0) {
                val sub = subjectRepository
                    .getOne(subjectId)
                    .first()

                if (sub != null) {

                    state.edit {
                        append(sub.title)
                    }
                }
            }
        }
    }

    fun addSubject() {
        viewModelScope.launch {
            _update.update { Update.Saving }
            subjectRepository.upsert(
                Subject(
                    id = subjectId,
                    seriesId = seriesId,
                    title = state.text.toString(),
                ),
            )
            _update.update { Update.Success }
        }
    }
}
