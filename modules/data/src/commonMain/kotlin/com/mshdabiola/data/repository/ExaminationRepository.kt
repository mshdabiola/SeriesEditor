package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asExam
import com.mshdabiola.data.model.asExamEntity
import com.mshdabiola.database.DatabaseExportImport
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.generalmodel.Examination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

internal class ExaminationRepository constructor(
    private val examinationDao: ExaminationDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val exportImport: DatabaseExportImport,
) : IExaminationRepository {

    private val _isSelectMode = MutableStateFlow(false)
    override val isSelectMode = _isSelectMode.asStateFlow()

    private val _selectedList = MutableStateFlow<List<Long>>(emptyList())
    override val selectedList: Flow<List<Long>> = _selectedList.asStateFlow()

    override suspend fun upsert(examination: Examination): Long {
        return withContext(ioDispatcher) {
            examinationDao.upsert(examination.asExamEntity())
        }
    }

    override fun getAll(): Flow<List<Examination>> {
        return examinationDao
            .getAll()
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllBuSubjectId(subjectId: Long): Flow<List<Examination>> {
        return examinationDao
            .getAllBySubjectId(subjectId)
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Examination?> {
        return examinationDao
            .getOne(id)
            .map { it?.asExam() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            examinationDao.delete(id)
        }
    }

    override suspend fun export(
        examsId: List<Long>,
        path: String,
        name: String,
        version: Int,
        key: String,
    ) {
        exportImport.export(examsId.toSet(), path, name, version, key)
    }

    override suspend fun import(path: String, key: String) {
        exportImport.import(path, key)
    }

    override  fun updateSelect(isSelect: Boolean) {
        _isSelectMode.update {
            isSelect
        }
    }

    override  fun updateSelectedList(selectedList: List<Long>) {
       _selectedList.update {
           selectedList
       }
    }
}
