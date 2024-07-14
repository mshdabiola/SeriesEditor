package com.mshdabiola.data.repository

import com.mshdabiola.database.DatabaseExportImport
import com.mshdabiola.database.ExportImport
import com.mshdabiola.database.asEntity
import com.mshdabiola.database.asExam
import com.mshdabiola.database.asModel
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.generalmodel.Examination
import com.mshdabiola.generalmodel.ExaminationWithSubject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

internal class ExaminationRepository constructor(
    private val examinationDao: ExaminationDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val exportImport: ExportImport,
) : IExaminationRepository {

    private val _isSelectMode = MutableStateFlow(false)
    override val isSelectMode = _isSelectMode.asStateFlow()

    private val _selectedList = MutableStateFlow<List<Long>>(emptyList())
    override val selectedList: Flow<List<Long>> = _selectedList.asStateFlow()

    override suspend fun upsert(examination: Examination): Long {
        return withContext(ioDispatcher) {
            examinationDao.upsert(examination.asEntity())
        }
    }

    override fun getAll(): Flow<List<Examination>> {
        return examinationDao
            .getAll()
            .map { fullList ->
                fullList.map { it.asModel() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllWithSubject(): Flow<List<ExaminationWithSubject>> {
        return examinationDao
            .getAllWithSubject()
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllBuSubjectId(subjectId: Long): Flow<List<ExaminationWithSubject>> {
        return examinationDao
            .getAllBySubjectIdWithSubject(subjectId)
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<ExaminationWithSubject?> {
        return examinationDao
            .getOneWithSubject(id)
            .map { it?.asExam() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            examinationDao.delete(id)
        }
    }

    override suspend fun export(examsId: Set<Long>, outputStream: OutputStream, password: String) {
        exportImport.export(examsId, outputStream, password)
    }

    override suspend fun import(inputStream: InputStream, password: String) {
        exportImport.import(inputStream, password)
    }


    override fun updateSelect(isSelect: Boolean) {
        _isSelectMode.update {
            isSelect
        }
    }

    override fun updateSelectedList(selectedList: List<Long>) {
        _selectedList.update {
            selectedList
        }
    }
}
