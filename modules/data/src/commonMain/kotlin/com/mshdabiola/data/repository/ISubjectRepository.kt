package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.Subject
import com.mshdabiola.generalmodel.SubjectWithSeries
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {

    suspend fun upsert(subject: Subject): Long
    fun getAll(): Flow<List<Subject>>

    fun getOne(id: Long): Flow<Subject?>

    fun getAllWithSeries(): Flow<List<SubjectWithSeries>>

    fun getOneWithSeries(id: Long): Flow<SubjectWithSeries?>

    suspend fun delete(id: Long)
}
