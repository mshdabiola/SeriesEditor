package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.seriesmodel.Examination
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import com.mshdabiola.testing.exportableData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.io.InputStream
import java.io.OutputStream

internal class FakeExaminationRepository : IExaminationRepository {

    private val _isSelect = MutableStateFlow(false)

    override val isSelectMode: Flow<Boolean>
        get() = _isSelect.asStateFlow()

    private val _selectedList = MutableStateFlow<List<Long>>(emptyList())

    override val selectedList: Flow<List<Long>>
        get() = _selectedList.asStateFlow()

    private val _exams = MutableStateFlow(exportableData.examinations)

    override suspend fun upsert(examination: Examination): Long {
        _exams.update {
            it.toMutableList().apply {
                if (examination.id == -1L) {
                    add(examination)
                } else {
                    val index = this.indexOfFirst { it.id == examination.id }
                    add(index, examination)
                }
            }
        }
        return 1
    }

    override fun getAll(): Flow<List<Examination>> {
        return _exams
    }

    override fun getAllBuSubjectId(subjectId: Long): Flow<List<ExaminationWithSubject>> {
        return _exams
            .map { it.filter { it.subjectId == subjectId } }
            .map {
                it.map {
                    val subject = exportableData.subjects.first { it.id == subjectId }
                    val series = exportableData.series.first { it.id == subject.seriesId }
                    ExaminationWithSubject(it, subject, series)
                }
            }
    }

    override fun getAllWithSubject(): Flow<List<ExaminationWithSubject>> {
        return _exams
            .map {
                it.map {
                    val subject = exportableData.subjects.first { it.id == it.id }
                    val series = exportableData.series.first { it.id == subject.seriesId }
                    ExaminationWithSubject(it, subject, series)
                }
            }
    }

    override fun getOne(id: Long): Flow<ExaminationWithSubject?> {
        return _exams
            .map { it.firstOrNull { it.id == id } }
            .map { examinations ->
                examinations?.let { examination ->
                    val subject = exportableData.subjects.first { it.id == examination.subjectId }
                    val series = exportableData.series.first { it.id == subject.seriesId }
                    ExaminationWithSubject(examination, subject, series)
                }
            }
    }

    override suspend fun delete(id: Long) {
        _exams
            .update {
                it.toMutableList().apply {
                    removeIf { it.id == id }
                }
            }
    }

    override suspend fun export(
        examsId: Set<Long>,
        outputStream: OutputStream,
        password: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun import(inputStream: InputStream, password: String) {
        TODO("Not yet implemented")
    }

    override fun updateSelect(isSelect: Boolean) {
        _isSelect.value = isSelect
    }

    override fun updateSelectedList(selectedList: List<Long>) {
        _selectedList.value = selectedList
    }
}
