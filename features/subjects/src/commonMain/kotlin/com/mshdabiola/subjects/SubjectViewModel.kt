/*
 *abiola 2022
 */

package com.mshdabiola.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.seriesmodel.SubjectWithSeries
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SubjectViewModel constructor(
    private val iSubjectRepository: ISubjectRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {


    val subjects: StateFlow<Result<List<SubjectWithSeries>>> =
        combine(
            iSubjectRepository.getAllWithSeries(),
            userDataRepository.userData.map { it.userId },
        ) { list, userId ->
            Pair(list, userId)
        }
            .map { triple ->
                triple.first
                    .filter { it.series.userId == triple.second }
            }
            .asResult()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun onDelete(id: Long) {
        viewModelScope.launch {
         iSubjectRepository.delete(id)
        }
    }

}
