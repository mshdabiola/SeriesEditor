package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.Examination
import kotlinx.coroutines.flow.Flow

interface IExaminationRepository {

    val isSelectMode :Flow<Boolean>
    val selectedList : Flow<List<Long>>

    suspend fun upsert(examination: Examination): Long
    fun getAll(): Flow<List<Examination>>

    fun getAllBuSubjectId(subjectId: Long): Flow<List<Examination>>

    fun getOne(id: Long): Flow<Examination?>

    suspend fun delete(id: Long)

    suspend fun export(
        examsId: List<Long>,
        path: String,
        name: String,
        version: Int,
        key: String,
    )

    suspend fun import(
        path: String,
        key: String,
    )

     fun updateSelect(isSelect: Boolean)
     fun updateSelectedList(selectedList: List<Long>)
}
