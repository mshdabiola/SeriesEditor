/*
 *abiola 2022
 */

package com.mshdabiola.serieseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.SeriesRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.toWord
import com.mshdabiola.model.Platform
import com.mshdabiola.model.UserData
import com.mshdabiola.model.currentPlatform
import com.mshdabiola.serieseditor.MainActivityUiState.Loading
import com.mshdabiola.serieseditor.MainActivityUiState.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainAppViewModel(
    userDataRepository: UserDataRepository,
//    userRepository: UserRepository,
    private val iExamRepository: IExaminationRepository,
    private val questionRepository: IQuestionRepository,
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Success())
    val mainState = _mainState.asStateFlow()


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


    fun onExport(path: String, key: String) {
        viewModelScope.launch {
            _mainState.value = MainState.Loading
            try {
                val ids = iExamRepository.selectedList.first().toSet()
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val currentDateTime = LocalDateTime.now() // Use LocalDateTime
                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")

                val nameByDate = "series_${formatter.format(currentDateTime)}.se"
                val outputStream =
                    File(file, nameByDate).apply { createNewFile() }.outputStream()

                iExamRepository.export(ids, outputStream, key)
                deselectAll()
                val messeage = if (Platform.Android == currentPlatform) {
                    "Successfully exported to internal storage, series directory"
                } else {
                    "successfully exported to desktop, series directory"
                }
                _mainState.value = MainState.Success(messeage)
            } catch (e: Exception) {
                e.printStackTrace()
                deselectAll()

                _mainState.value = MainState.Success("Failed to export")
            }

            delay(1500)

            _mainState.value = MainState.Success("")
        }
    }

    fun onExportWord(path: String) {
        viewModelScope.launch {
            _mainState.value = MainState.Loading
            try {
                val ids = iExamRepository.selectedList.first().toSet()
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }

                ids
                    .mapNotNull { iExamRepository.getOne(it).first() }
                    .forEach {
                        val name =
                            "${it.examination.id}-${it.subject.title}-${it.examination.year}.docx"
                        val newPath = File(file, name)
                        val questions = questionRepository.getByExamId(it.examination.id).first()
                        toWord(newPath.path, it, questions)
                    }

                deselectAll()
                val messeage = if (Platform.Android == currentPlatform) {
                    "Successfully Saved to internal storage, series directory"
                } else {
                    "successfully Saved to desktop, series directory"
                }
                _mainState.value = MainState.Success(messeage)
            } catch (e: Exception) {
                e.printStackTrace()
                deselectAll()

                _mainState.value = MainState.Success("Failed to export")
            }

            delay(1500)

            _mainState.value = MainState.Success("")
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
                (
                        if (subjectId < 0) {
                            iExamRepository.getAll()
                                .mapNotNull { it.map { it.id } }
                        } else {
                            iExamRepository
                                .getAllBuSubjectId(subjectId)
                                .mapNotNull { it.map { it.examination.id } }
                        }
                        ).first()

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
