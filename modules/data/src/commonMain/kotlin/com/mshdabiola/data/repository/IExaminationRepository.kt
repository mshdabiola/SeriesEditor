package com.mshdabiola.data.repository

import com.mshdabiola.seriesmodel.Examination
import com.mshdabiola.seriesmodel.ExaminationWithSubject
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import java.io.OutputStream

interface IExaminationRepository {

    val isSelectMode: Flow<Boolean>
    val selectedList: Flow<List<Long>>

    suspend fun upsert(examination: Examination): Long
    fun getAll(): Flow<List<Examination>>

    fun getAllBuSubjectId(subjectId: Long): Flow<List<ExaminationWithSubject>>

    fun getAllWithSubject(): Flow<List<ExaminationWithSubject>>
    fun getOne(id: Long): Flow<ExaminationWithSubject?>

    suspend fun delete(id: Long)

    suspend fun export(
        examsId: Set<Long>,
        outputStream: OutputStream,
        password: String,
    )

    suspend fun import(
        inputStream: InputStream,
        password: String,
    )

    fun updateSelect(isSelect: Boolean)
    fun updateSelectedList(selectedList: List<Long>)
}
