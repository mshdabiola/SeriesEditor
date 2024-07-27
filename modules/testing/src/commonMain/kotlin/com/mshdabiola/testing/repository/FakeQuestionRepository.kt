package com.mshdabiola.testing.repository

import co.touchlab.kermit.Logger
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.database.asEntity
import com.mshdabiola.database.asModel
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.testing.questions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class FakeQuestionRepository: IQuestionRepository {
    private val  _question = MutableStateFlow(questions)

    override suspend fun upsert(question: Question): Long {
        _question.value = _question.value.toMutableList().apply {
            if (question.id == -1L) {
                add(question)
            }else{
                val index = this.indexOfFirst { it.id == question.id }
                add(index, question)
            }
        }
        return 1
    }

    override suspend fun upsertMany(question: List<Question>) {
       question.forEach {
           upsert(it)
       }
    }

    override fun getAll(): Flow<List<Question>> {
       return _question

    }

    override fun getOne(id: Long): Flow<Question?> {
       return _question
           .map { it.firstOrNull { it.id == id } }
    }

    override fun getByExamId(examId: Long): Flow<List<Question>> {
        return _question
            .map { it.filter { it.examId == examId } }
    }

    override fun getByRandom(number: Long): Flow<List<Question>> {
        return _question
            .map { it.shuffled().take(number.toInt()) }
    }

    override suspend fun delete(id: Long) {
       _question.value = _question.value.toMutableList().apply {
           removeIf { it.id == id }
       }
    }

    override suspend fun deleteOption(id: Long) {
       _question.value = _question.value.toMutableList().apply {
           removeIf { it.id == id }
       }
    }

}
