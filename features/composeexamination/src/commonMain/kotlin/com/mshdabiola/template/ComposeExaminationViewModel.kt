/*
 *abiola 2022
 */

package com.mshdabiola.template

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.generalmodel.Examination
import com.mshdabiola.ui.toSubject
import com.mshdabiola.ui.toUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)

class ComposeExaminationViewModel(
    private val examId: Long,
    private val subjectRepository: ISubjectRepository,
    private val examRepository: IExaminationRepository,
) : ViewModel() {

    val subjects = subjectRepository
        .getAll()
        .map { subjectList -> subjectList.map { it.toUi() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf(),
        )


    val duration = TextFieldState()
    val year = TextFieldState()
    val subject = TextFieldState()

    init {
        viewModelScope.launch {
            val initExam = examRepository
                .getOne(examId)
                .first()

            if (initExam != null) {
                subject.edit {
                    append(initExam.subject.title)
                }
                year.edit {
                    append(initExam.year.toString())
                }
                duration.edit {
                    append(initExam.duration.toString())
                }
            } else {
                delay(2000)
                subject.edit {
                    append(subjects.value.firstOrNull()?.name ?: "")
                }
            }
        }
    }


    fun addExam() {
        viewModelScope.launch {
            val subject = subjects.value.single { it.name == subject.text.toString() }
            val exam = Examination(
                id = if (examId > 0) examId else null,
                duration = duration.text.toString().toLong(),
                year = year.text.toString().toLong(),
                subject = subject.toSubject(),
                isObjectiveOnly = true,
                updateTime = System.currentTimeMillis(),
            )
            examRepository.upsert(exam)


        }
    }


}