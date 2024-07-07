package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {

    suspend fun upsert(subject: Subject): Long
    fun getAll(): Flow<List<Subject>>

    fun getOne(id: Long): Flow<Subject?>

    suspend fun delete(id: Long)
}
