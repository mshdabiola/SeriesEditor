package com.mshdabiola.datastore

import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import kotlinx.coroutines.flow.Flow

interface Store {

    val userData: Flow<UserDataSer>
    val questions: Flow<Map<Long, Question>>
    val instructions: Flow<Map<Long, Instruction>>

    suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer

    suspend fun updateQuestion(transform: suspend (Map<Long, Question>) -> Map<Long, Question>): Map<Long, Question>
    suspend fun updateInstruction(transform: suspend (Map<Long, Instruction>) -> Map<Long, Instruction>): Map<Long, Instruction>
}
