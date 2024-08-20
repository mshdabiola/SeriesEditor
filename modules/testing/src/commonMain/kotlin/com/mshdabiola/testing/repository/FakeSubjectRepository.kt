package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.seriesmodel.Subject
import com.mshdabiola.seriesmodel.SubjectWithSeries
import com.mshdabiola.testing.exportableData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeSubjectRepository : ISubjectRepository {
    private val _subject = MutableStateFlow<List<Subject>>(exportableData.subjects)

    override suspend fun upsert(subject: Subject): Long {
        _subject.value = _subject.value.toMutableList().apply {
            if (subject.id == -1L) {
                add(subject)
            } else {
                val index = this.indexOfFirst { it.id == subject.id }
                add(index, subject)
            }
        }
        return 1
    }

    override fun getAll(): Flow<List<Subject>> {
        return _subject
    }

    override fun getOne(id: Long): Flow<Subject?> {
        return _subject
            .map { it.firstOrNull { it.id == id } }
    }

    override fun getAllWithSeries(): Flow<List<SubjectWithSeries>> {
        return _subject
            .map { subjects ->
                subjects.map { sub ->
                    val series = exportableData.series.first { sub.seriesId == it.id }
                    SubjectWithSeries(sub, series)
                }
            }
    }

    override fun getOneWithSeries(id: Long): Flow<SubjectWithSeries?> {
        return _subject
            .map { it.firstOrNull { it.id == id } }
            .map { subject ->
                subject?.let {
                    val series = exportableData.series.first { it.id == subject.seriesId }
                    SubjectWithSeries(subject, series)
                }
            }
    }

    override suspend fun delete(id: Long) {
        _subject.value = _subject.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }
}
