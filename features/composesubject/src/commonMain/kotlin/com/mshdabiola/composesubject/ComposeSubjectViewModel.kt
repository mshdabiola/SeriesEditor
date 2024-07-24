/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.mshdabiola.data.model.Update
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class ComposeSubjectViewModel(
    private val subjectId: Long,
    private val seriesRepository: ISeriesRepository,
    private val subjectRepository: ISubjectRepository,
    private val userDataRepository: UserDataRepository,
    private val logger: Logger,
) : ViewModel() {

    @OptIn(ExperimentalFoundationApi::class)
    val subjectState = TextFieldState()

    val seriesState = TextFieldState()

//    private val userId = userDataRepository
//        .userData
//        .map { it.userId }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), -1L)

    val series = seriesRepository
        .getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentSeriesId = mutableStateOf(1L)

    private val _update = MutableStateFlow(Update.Edit)
    val update = _update.asStateFlow()

    init {

        viewModelScope.launch {
            if (subjectId > 0) {
                val sub = subjectRepository
                    .getOneWithSeries(subjectId)
                    .first()

                if (sub != null) {

                    subjectState.edit {
                        append(sub.subject.title)
                    }

                    kotlinx.coroutines.delay(1500)
                    currentSeriesId.value = sub.series.id
                    if (sub.series.id > 1) {

                        seriesState.edit {
                            append(sub.series.name)
                        }
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
                    seriesId = currentSeriesId.value,
                    title = subjectState.text.toString(),
                ),
            )
            _update.update { Update.Success }
        }
    }

    fun onCurrentSeriesChange(id: Long) {
        currentSeriesId.value = id

        seriesState.clearText()

        if (id > 1) {
            seriesState.edit {
                append(series.value.find { it.id == id }?.name)
            }
        }
    }

    fun addSeries() {
        viewModelScope.launch {
            val id = if (currentSeriesId.value == 1L) -1 else currentSeriesId.value
            val userId = userDataRepository
                .userData.first()
                .userId

            val newId = seriesRepository.upsert(
                series = Series(
                    id = id,
                    userId = userId,
                    name = seriesState.text.toString(),
                ),
            )
            if (newId > 0) {
                currentSeriesId.value = newId
            }
        }
    }

    fun onDeleteCurrentSeries() {
        viewModelScope.launch {
            seriesRepository.delete(currentSeriesId.value)
            seriesState.clearText()
            currentSeriesId.value = 1
        }
    }
}
