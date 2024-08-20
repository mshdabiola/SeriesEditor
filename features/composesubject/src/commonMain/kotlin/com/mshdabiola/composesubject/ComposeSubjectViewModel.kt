/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.seriesmodel.Subject
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

    val subjectState = TextFieldState()

    private val _csState = MutableStateFlow<CsState>(CsState.Loading())
    val csState = _csState.asStateFlow()

    init {

        viewModelScope.launch {

            val sub = subjectRepository
                .getOneWithSeries(subjectId)
                .first()

            if (sub != null) {

                subjectState.edit {
                    append(sub.subject.title)
                }
            }
            _csState.update {
                CsState.Success(
                    id = seriesId,
                )
            }
        }
    }

    fun addSubject() {
        viewModelScope.launch {
            val subject = Subject(
                id = subjectId,
                seriesId = seriesId,
                title = subjectState.text.toString(),
            )
            _csState.update { CsState.Loading() }
            subjectRepository.upsert(
                subject,
            )
            _csState.update { CsState.Loading(true) }
        }
    }
}
