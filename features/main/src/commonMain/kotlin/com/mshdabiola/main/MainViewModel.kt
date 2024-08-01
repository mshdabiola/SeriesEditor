/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class MainViewModel constructor(
    private val iSubjectRepository: ISubjectRepository,
    private val iExamRepository: IExaminationRepository,
    private val userDataRepository: UserDataRepository,
    val subjectId: Long,

) : ViewModel() {

//    private val _mainState = MutableStateFlow(MainState())
//    val mainState = _mainState.asStateFlow()

    val isSelectMode = iExamRepository
        .isSelectMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // private val _examUiStates = MutableStateFlow<Result<List<ExamUiState>>>(Result.Loading)

    val examUiMainState: StateFlow<Result<List<ExamUiState>>> =
        combine(
            iExamRepository.getAllWithSubject(),
            iExamRepository.selectedList,
            userDataRepository.userData.map { it.userId },
        ) { list, ids, userId ->
            Triple(list, ids, userId)
        }
            .map { triple ->
                triple.first
                    .filter { it.series.userId == triple.third }
                    .filter {
                        if (subjectId > 0) {
                            it.subject.id == subjectId
                        } else {
                            true
                        }
                    }
                    .map { it.toUi().copy(isSelected = triple.second.contains(it.examination.id)) }
            }
            .asResult()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Result.Loading)

    fun onDeleteExam(id: Long) {
        viewModelScope.launch {
            iExamRepository.delete(id)
        }
    }

    fun toggleSelect(index: Long) {
        viewModelScope.launch {
            val examSelect = iExamRepository.selectedList
                .first()
                .toMutableList()

            if (examSelect.contains(index)) {
                examSelect.remove(index)
                iExamRepository.updateSelectedList(examSelect)
                if (examSelect.isEmpty()) {
                    iExamRepository.updateSelect(false)
                }
            } else {
                examSelect.add(index)

                iExamRepository.updateSelectedList(examSelect)
                iExamRepository.updateSelect(true)
            }
        }

        //  _examUiStates.value = exams.toImmutableList()
    }
}
