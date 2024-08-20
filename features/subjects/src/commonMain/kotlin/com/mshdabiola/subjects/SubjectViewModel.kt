/*
 *abiola 2022
 */

package com.mshdabiola.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.seriesmodel.SubjectWithSeries
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SubjectViewModel(
    private val seriesId: Long,
    private val iSubjectRepository: ISubjectRepository,
) : ViewModel() {

    val subjects: StateFlow<Result<List<SubjectWithSeries>>> =
        iSubjectRepository.getAllWithSeries()
            .map { subjectWithSeries ->
                subjectWithSeries.filter { it.series.id == seriesId }
            }
            .asResult()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun onDelete(id: Long) {
        viewModelScope.launch {
            iSubjectRepository.delete(id)
        }
    }
}
