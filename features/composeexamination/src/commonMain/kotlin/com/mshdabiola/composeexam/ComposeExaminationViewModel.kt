/*
 *abiola 2022
 */

package com.mshdabiola.composeexam

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.seriesmodel.Examination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComposeExaminationViewModel(
    private val subjectId: Long,
    private val examId: Long,
    private val examRepository: IExaminationRepository,
) : ViewModel() {

    private val _ceState = MutableStateFlow<CeState>(CeState.Loading())
    val ceState = _ceState.asStateFlow()
    val duration = TextFieldState("15")
    val year = TextFieldState("")

    init {
        viewModelScope.launch {
            val initExam = examRepository
                .getOne(examId)
                .first()

            val isUpdate = if (initExam != null) {
                year.clearText()
                duration.clearText()

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
            _ceState.update {

                CeState.Success(isUpdate)
            }
        }
    }

    fun addExam() {
        viewModelScope.launch {
            _ceState.update { CeState.Loading() }
            val exam = Examination(
                id = examId,
                duration = duration.text.toString().toLong(),
                year = year.text.toString().toLong(),
                subjectId = subjectId,
            )
            examRepository.upsert(exam)

            _ceState.update { CeState.Loading(isLoading = true) }
        }
    }
}
