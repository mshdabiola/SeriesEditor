/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.seriesmodel.Series
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val seriesRepository: ISeriesRepository,
    private val subjectRepository: ISubjectRepository,
    private val examRepository: IExaminationRepository,
    private val questionRepository: IQuestionRepository,
    private val userRepository: IUserRepository,
) : ViewModel() {

    val classState = TextFieldState()

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var currentId: Long = -1
    private val user = userRepository
        .getUser(1)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        viewModelScope.launch {
            combine(
                seriesRepository.getAll(),
                subjectRepository.getAll(),
                examRepository.getAll(),
                questionRepository.getAll(),
            ) { series, subject, exam, question ->
                Pair(series, Triple(subject, exam, question))
            }.collectLatest { triplePair ->
                val id = user.value?.id ?: 1
                val series = triplePair.first.filter { it.userId == id }
                val subjects =
                    triplePair.second.first.filter { subject -> subject.seriesId in series.map { it.id } }
                val exams =
                    triplePair.second.second.filter { subject -> subject.subjectId in subjects.map { it.id } }

                val questions =
                    triplePair.second.third.filter { subject -> subject.examId in exams.map { it.id } }

                _mainState.update {
                    it.copy(
                        series = series,
                        subjectNumber = subjects.count(),
                        examNumber = exams.count(),
                        questionNumber = questions.count(),
                    )
                }
            }
        }
    }

    fun addClass() {
        viewModelScope.launch {
            seriesRepository.upsert(
                Series(
                    currentId,
                    user.value?.id ?: 1,
                    classState.text.toString(),
                ),
            )

            currentId = -1
            classState.clearText()
        }
    }

    fun deleteClass(id: Long) {
        viewModelScope.launch {
            seriesRepository.delete(id)
        }
    }

    fun updateClass(id: Long) {
        viewModelScope.launch {
            val series = seriesRepository.getOne(id).first()
            if (series != null) {
                currentId = series.id
                classState.clearText()
                classState.edit {
                    this.append(series.name)
                }
            }
        }
    }
}
