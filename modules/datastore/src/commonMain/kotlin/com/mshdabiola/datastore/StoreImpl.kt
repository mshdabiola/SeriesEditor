package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Question
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class StoreImpl(
    private val userdata: DataStore<UserDataSer>,
    private val question: DataStore<Map<Long, Question>>,
    private val instruction: DataStore<Map<Long, Instruction>>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : Store {

    override val userData: Flow<UserDataSer>
        get() = userdata
            .data
            .flowOn(coroutineDispatcher)
    override val questions: Flow<Map<Long, Question>>
        get() = question
            .data
            .flowOn(coroutineDispatcher)
    override val instructions: Flow<Map<Long, Instruction>>
        get() = instruction
            .data
            .flowOn(coroutineDispatcher)


    override suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer {
        return withContext(coroutineDispatcher) {
            userdata.updateData(transform)
        }
    }

    override suspend fun updateQuestion(transform: suspend (Map<Long, Question>) -> Map<Long, Question>): Map<Long, Question> {
        return withContext(coroutineDispatcher) {
            question.updateData(transform)
        }
    }

    override suspend fun updateInstruction(transform: suspend (Map<Long, Instruction>) -> Map<Long, Instruction>): Map<Long, Instruction> {
        return withContext(coroutineDispatcher) {
            instruction.updateData(transform)
        }
    }


}
