package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.testing.questions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeQuestionRepository : IQuestionRepository {
    private val _question = MutableStateFlow(questions)

    override suspend fun upsert(question: Question): Long {
        _question.value = _question.value.toMutableList().apply {
            if (question.id == -1L) {
                add(question)
            } else {
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
