package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FakeSettingRepository : ISettingRepository {

    private val _instruction = MutableStateFlow<Map<Long, Instruction>>(emptyMap())
    private val _question = MutableStateFlow<Map<Long, Question>>(emptyMap())

    override val instructions: Flow<Map<Long, Instruction>>
        get() = _instruction.asStateFlow()
    override val questions: Flow<Map<Long, Question>>
        get() = _question.asStateFlow()

    override suspend fun setCurrentInstruction(instruction: Map<Long, Instruction>) {
        _instruction.value = instruction
    }

    override suspend fun setCurrentQuestion(question: Map<Long, Question>) {
        _question.value = question
    }
}
