package com.mshdabiola.data.repository

import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.asModel
import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesmodel.Instruction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class InstructionRepository(
    private val instructionDao: InstructionDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IInstructionRepository {
    override suspend fun upsert(instruction: Instruction): Long {
        return withContext(ioDispatcher) {
            instructionDao.upsert(instruction.asEntity())
        }
    }

    override suspend fun insertAll(instruction: List<Instruction>) {
        return withContext(ioDispatcher) {
            instructionDao.insertAll(instruction.map { it.asEntity() })
        }
    }

    override fun getAll(): Flow<List<Instruction>> {
        return instructionDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllByExamId(examId: Long): Flow<List<Instruction>> {
        return instructionDao
            .getAllByExamId(examId)
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Instruction?> {
        return instructionDao
            .getOne(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            instructionDao.delete(id)
        }
    }
}
