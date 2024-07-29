package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.testing.instructions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeInstructionRepository : IInstructionRepository {

    private val _instruction = MutableStateFlow(instructions)

    override suspend fun upsert(instruction: Instruction): Long {
        _instruction.value = _instruction.value.toMutableList().apply {
            if (instruction.id == -1L) {
                add(instruction)
            } else {
                val index = this.indexOfFirst { it.id == instruction.id }
                add(index, instruction)
            }
        }
        return 1
    }

    override suspend fun insertAll(instruction: List<Instruction>) {
        _instruction.value = _instruction.value.toMutableList().apply {
            addAll(instruction)
        }
    }

    override fun getAll(): Flow<List<Instruction>> {
        return _instruction
    }

    override fun getAllByExamId(examId: Long): Flow<List<Instruction>> {
        return _instruction
            .map { it.filter { it.examId == examId } }
    }

    override fun getOne(id: Long): Flow<Instruction?> {
        return _instruction
            .map { it.firstOrNull { it.id == id } }
    }

    override suspend fun delete(id: Long) {
        _instruction.value = _instruction.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }
}
