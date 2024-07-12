/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.SeriesRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.UserRepository
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.User
import com.mshdabiola.generalmodel.UserType
import com.mshdabiola.model.UserData
import com.mshdabiola.serieseditor.MainActivityUiState.Loading
import com.mshdabiola.serieseditor.MainActivityUiState.Success
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainAppViewModel(
    userDataRepository: UserDataRepository,
    subjectRepository: ISubjectRepository,
    userRepository: UserRepository,
    private val iExamRepository: IExaminationRepository,
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {

        viewModelScope.launch {
            val id =userDataRepository.userData
                .first()
                .userId

            _user.value = userRepository.getUser().first()




            if(user.value==null){
                val userr= User(
                    id = -1,
                    name = "Abiola",
                    type = UserType.TEACHER,
                    password = "cheatmobi",
                    imagePath = "",
                    points = 1
                )
                _user.value=userr
                userRepository.setUser(userr)

                seriesRepository.upsert(Series(-1, userId = userr.id, "Default"))

            }




        }
    }


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
        .getAllWithSeries()
        .map { subjectList -> subjectList.map { it.toUi() } }
        // .asResult()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

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

    fun selectAll(subjectId: Long) {
        viewModelScope.launch {
            val list =
               ( if (subjectId < 0) {
                    iExamRepository.getAll()
                        .mapNotNull { it.map { it.id }}
                } else {
                    iExamRepository
                        .getAllBuSubjectId(subjectId)
                        .mapNotNull { it.map { it.examination.id }}
                }).first()


            iExamRepository.updateSelectedList(list)
            iExamRepository.updateSelect(true)
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
