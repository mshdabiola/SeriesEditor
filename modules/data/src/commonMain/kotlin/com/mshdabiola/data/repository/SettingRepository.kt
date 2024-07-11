package com.mshdabiola.data.repository

import com.mshdabiola.datastore.Store
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.model.data.CurrentExam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingRepository(
    private val settings: Store,

) : ISettingRepository {
    override val instructions: Flow<Map<Long, Instruction>>
        get() = settings
            .instructions

    override val questions: Flow<Map<Long, Question>>
        get() = settings
            .questions


    override suspend fun setCurrentInstruction(instruction: Map<Long, Instruction>) {
        settings.updateInstruction { instruction.mapValues { it.value } }
    }

    override suspend fun setCurrentQuestion(question: Map<Long, Question>) {
        settings.updateQuestion { question.mapValues { it.value } }
    }


}
