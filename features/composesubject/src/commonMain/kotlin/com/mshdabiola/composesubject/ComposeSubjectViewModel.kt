/*
 *abiola 2022
 */

package com.mshdabiola.composesubject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
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

                if (sub.series.id > 1) {

                    seriesState.edit {
                        append(sub.series.name)
                    }
                }
            }
            _csState.update {
                CsState.Success(
                    currentSeries = sub?.series?.id ?: 1,
                )
            }


            seriesRepository
                .getAll()
                .collectLatest { list ->
                    _csState.update {
                        if (it is CsState.Success) {
                            it.copy(series = list)
                        } else it
                    }

                }

        }
    }

    fun addSubject() {
        viewModelScope.launch {
            val subject= Subject(
                id = subjectId,
                seriesId = (csState.value as CsState.Success).currentSeries,
                title = subjectState.text.toString(),
            )
            _csState.update { CsState.Loading() }
            subjectRepository.upsert(
               subject
            )
            _csState.update { CsState.Loading(true) }
        }
    }

    fun onCurrentSeriesChange(id: Long) {
        _csState.update {
            if (it is CsState.Success) {
                it.copy(currentSeries = id)
            } else it
        }
        seriesState.clearText()

        if (id > 1) {
            seriesState.edit {
                append((csState.value as CsState.Success).series .find { it.id == id }?.name)
            }
        }
    }

    fun addSeries() {
        viewModelScope.launch {
            val currentSeriesId = (csState.value as CsState.Success).currentSeries
            val id = if (currentSeriesId == 1L) -1 else currentSeriesId
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
                _csState.update {
                    if (it is CsState.Success) {
                        it.copy(currentSeries = newId)
                    } else it
                }
            }
        }
    }

    fun onDeleteCurrentSeries() {
        viewModelScope.launch {
            val currentSeriesId = (csState.value as CsState.Success).currentSeries
            seriesRepository.delete(currentSeriesId)
            seriesState.clearText()
            _csState.update { state ->
                state.getSuccess {
                    it.copy(currentSeries = 1)
                }
            }
        }
    }
}
