package com.mshdabiola.datastore

import com.mshdabiola.model.UserData
import com.mshdabiola.seriesmodel.Instruction
import com.mshdabiola.seriesmodel.Question
import kotlinx.coroutines.flow.Flow

interface Store {

    val userData: Flow<UserData>
    val questions: Flow<Map<Long, Question>>
    val instructions: Flow<Map<Long, Instruction>>

    suspend fun updateUserData(transform: suspend (UserData) -> UserData): UserData

    suspend fun updateQuestion(transform: suspend (Map<Long, Question>) -> Map<Long, Question>): Map<Long, Question>
    suspend fun updateInstruction(transform: suspend (Map<Long, Instruction>) -> Map<Long, Instruction>): Map<Long, Instruction>
}
