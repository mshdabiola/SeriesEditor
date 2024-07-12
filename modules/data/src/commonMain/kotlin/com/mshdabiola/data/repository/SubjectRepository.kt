package com.mshdabiola.data.repository

import com.mshdabiola.database.dao.exam.SubjectDao
import com.mshdabiola.database.model.asEntity
import com.mshdabiola.database.model.asModel
import com.mshdabiola.generalmodel.Subject
import com.mshdabiola.generalmodel.SubjectWithSeries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class SubjectRepository constructor(
    private val subjectDao: SubjectDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ISubjectRepository {
    override suspend fun upsert(subject: Subject): Long {
        return withContext(ioDispatcher) {
            subjectDao.upsert(subject.asEntity())
        }
    }

    override fun getAll(): Flow<List<Subject>> {

        return subjectDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Subject?> {
        return subjectDao
            .getOne(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override fun getAllWithSeries(): Flow<List<SubjectWithSeries>> {
        return subjectDao
            .getAllWithSeries()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOneWithSeries(id: Long): Flow<SubjectWithSeries?> {
        return subjectDao
            .getOneWithSeries(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            subjectDao.delete(id)
        }
    }
}
