package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import kotlinx.coroutines.flow.Flow

interface ISettingRepository {

    val instructions: Flow<Map<Long, Instruction>>
    val questions: Flow<Map<Long, Question>>

    suspend fun setCurrentInstruction(instruction: Map<Long, Instruction>)

    suspend fun setCurrentQuestion(question: Map<Long, Question>)
}
