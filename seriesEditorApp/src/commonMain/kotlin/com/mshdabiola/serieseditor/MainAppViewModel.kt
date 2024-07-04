/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.UserData
import com.mshdabiola.serieseditor.MainActivityUiState.Loading
import com.mshdabiola.serieseditor.MainActivityUiState.Success
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainAppViewModel(
    userDataRepository: UserDataRepository,
    subjectRepository: ISubjectRepository,
   private val iExamRepository: IExaminationRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    val isSelectMode = iExamRepository
        .isSelectMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    val subjects = subjectRepository
        .getAll()
        .map { subjectList -> subjectList.map { it.toUi() } }
       // .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    private val examUiMainState =
        iExamRepository
            .getAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    fun onExport(path: String, name: String, key: String, version: Int) {
        viewModelScope.launch {
            val ids = iExamRepository.selectedList.first()

            iExamRepository.export(ids, path, name, version, key)
            deselectAll()
        }
    }





    fun deselectAll() {
        viewModelScope.launch {

            iExamRepository.updateSelectedList(emptyList())
            iExamRepository.updateSelect(false)


        }
    }

    fun selectAll() {
        viewModelScope.launch {

                val list = examUiMainState
                    .value
                    .mapNotNull { it.id }

                iExamRepository.updateSelectedList(list)
                iExamRepository.updateSelect(false)


        }

    }


    fun deleteSelected() {
        viewModelScope.launch {
            iExamRepository.selectedList.first()
                .forEach {
                    iExamRepository.delete(it)
                }
            deselectAll()
        }
    }
    fun toggleSelectMode() {
        iExamRepository.updateSelect(!isSelectMode.value)
//        _isSelectMode.value = !isSelectMode.value
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
