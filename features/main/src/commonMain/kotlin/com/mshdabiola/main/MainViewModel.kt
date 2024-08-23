/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.data.repository.IUserRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.toWord
import com.mshdabiola.model.Platform
import com.mshdabiola.model.currentPlatform
import com.mshdabiola.seriesmodel.Series
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(
    private val seriesRepository: ISeriesRepository,
    private val subjectRepository: ISubjectRepository,
    private val examRepository: IExaminationRepository,
    private val questionRepository: IQuestionRepository,
    private val userRepository: IUserRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val classState = TextFieldState()

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    val passwordState = TextFieldState()
    private val _examState = MutableStateFlow<ExportState>(ExportState.Loading())
    val examState = _examState.asStateFlow()

    private var currentId: Long = -1
    private val userId = userDataRepository
        .userData
        .map { it.userId }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

    init {
        viewModelScope.launch {

            combine(
                userDataRepository
                    .userData
                    .map { it.userId },
                seriesRepository.getAll(),
                subjectRepository.getAll(),
                examRepository.getAll(),
                questionRepository.getAll(),
            ) { userId, series, subject, exam, question ->
                Triple(userId, series, Triple(subject, exam, question))
            }.collectLatest { triplePair ->
                val id = triplePair.first
                if (id > 0) {
                    val user = userRepository.getUser(id).first()!!
                    val series = triplePair.second.filter { it.userId == id }
                    val subjects =
                        triplePair.third.first.filter { subject -> subject.seriesId in series.map { it.id } }
                    val exams =
                        triplePair.third.second.filter { subject -> subject.subjectId in subjects.map { it.id } }

                    val questions =
                        triplePair.third.third.filter { subject -> subject.examId in exams.map { it.id } }

                    _mainState.update {
                        it.copy(
                            user = user,
                            series = series,
                            subjectNumber = subjects.count(),
                            examNumber = exams.count(),
                            questionNumber = questions.count(),
                        )
                    }
                }
            }
        }
    }

    fun addClass() {
        viewModelScope.launch {
            seriesRepository.upsert(
                Series(
                    currentId,
                    mainState.value.user.id,
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

    fun signOut() {
        viewModelScope.launch {
            userDataRepository.setUserId(-1)
        }
    }

    fun loadExams() {
        viewModelScope.launch {
            val list = examRepository
                .getAllWithSubject()
                .map { subjectList ->
                    subjectList
                        .filter { it.series.userId == userId.value }
                        .map {
                            ExamState(
                                id = it.examination.id,
                                subject = it.subject.title,
                                year = it.examination.year,
                                classRoom = it.series.name,
                                isSelected = false,
                            )
                        }
                }
                .first()
            println(list.joinToString())

            _examState.update {
                ExportState.Success(list)
            }

        }
    }


    fun onExport(path: String) {
        viewModelScope.launch {
            val list = (examState.value as ExportState.Success).exams
            _examState.value = ExportState.Loading()
            val key = passwordState.text.toString()
            try {
                val ids = list.filter { it.isSelected }
                    .map { it.id }
                    .toSet()
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val currentDateTime = LocalDateTime.now() // Use LocalDateTime
                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")

                val nameByDate = "series_${formatter.format(currentDateTime)}.se"
                val outputStream =
                    File(file, nameByDate).apply { createNewFile() }.outputStream()

                examRepository.export(ids, outputStream, key)
                val messeage = if (Platform.Android == currentPlatform) {
                    "Successfully exported to internal storage, series directory"
                } else {
                    "successfully exported to desktop, series directory"
                }
                _examState.value = ExportState.Loading(true)

            } catch (e: Exception) {
                e.printStackTrace()
                _examState.value = ExportState.Error(e)


//                _mainState.value = MainState.Success("Failed to export")
            }


            delay(1500)

            _examState.value = ExportState.Loading(false)
        }
    }

    fun onExportWord(path: String) {
        viewModelScope.launch {
            val list = (examState.value as ExportState.Success).exams
            _examState.value = ExportState.Loading()
            try {
                val ids = list.filter { it.isSelected }
                    .map { it.id }.toSet()
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }

                ids
                    .mapNotNull { examRepository.getOne(it).first() }
                    .forEach {
                        val name =
                            "${it.examination.id}-${it.subject.title}-${it.examination.year}.docx"
                        val newPath = File(file, name)
                        val questions = questionRepository.getByExamId(it.examination.id).first()
                        toWord(newPath.path, it, questions)
                    }

                val messeage = if (Platform.Android == currentPlatform) {
                    "Successfully Saved to internal storage, series directory"
                } else {
                    "successfully Saved to desktop, series directory"
                }
                _examState.value = ExportState.Loading(true)
            } catch (e: Exception) {
                e.printStackTrace()

                _examState.value = ExportState.Error(e)
            }
            delay(1500)

            _examState.value = ExportState.Loading(false)
        }
    }



    fun onSelect(id: Long) {
        val list = (examState.value as ExportState.Success).exams.toMutableList()
        val index = list.indexOfFirst { it.id == id }

        if (index != -1) {
            var updatedItem = list[index]

            updatedItem = updatedItem.copy(isSelected = !updatedItem.isSelected)
            println(updatedItem)

            list[index] = updatedItem

            _examState.update {
                ExportState.Success(list)
            }
        }
    }
}
