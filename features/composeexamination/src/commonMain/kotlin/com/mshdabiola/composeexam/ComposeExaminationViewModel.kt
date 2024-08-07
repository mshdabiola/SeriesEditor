/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.seriesmodel.Examination
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComposeExaminationViewModel(
    private val examId: Long,
    private val subjectRepository: ISubjectRepository,
    private val examRepository: IExaminationRepository,
) : ViewModel() {

    private val _ceState = MutableStateFlow<CeState>(CeState.Loading())
    val ceState = _ceState.asStateFlow()
    val duration = TextFieldState("15")
    val year = TextFieldState("")
    val subject = TextFieldState()

    init {
        viewModelScope.launch {
            val initExam = examRepository
                .getOne(examId)
                .first()

            val isUpdate = if (initExam != null) {
                subject.clearText()
                year.clearText()
                duration.clearText()
                subject.edit {
                    append(initExam.subject.title)
                }
                year.edit {
                    append(initExam.examination.year.toString())
                }
                duration.edit {
                    append(initExam.examination.duration.toString())
                }
                true
            } else {
                false
            }
            subjectRepository
                .getAllWithSeries()
                .map { subjectList -> subjectList.map { it.toUi() } }
                .collectLatest { list ->
                    _ceState.update {
                        if (subject.text.isBlank()) {
                            subject.edit {
                                append(list.firstOrNull()?.name ?: "")
                            }
                        }
                        CeState.Success(isUpdate, list)
                    }
                }
        }
    }

    fun addExam() {
        viewModelScope.launch {
            val subjects = (ceState.value as CeState.Success).subjects
            _ceState.update { CeState.Loading() }
            val subject = subjects.single { it.name == subject.text.toString() }
            val exam = Examination(
                id = examId,
                duration = duration.text.toString().toLong(),
                year = year.text.toString().toLong(),
                subjectId = subject.id,
            )
            examRepository.upsert(exam)

            _ceState.update { CeState.Loading(isLoading = true) }
        }
    }
}
