package com.mshdabiola.data.repository

import co.touchlab.kermit.Logger
import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.asModel
import com.mshdabiola.seriesdatabase.dao.OptionDao
import com.mshdabiola.seriesdatabase.dao.QuestionDao
import com.mshdabiola.seriesmodel.Question
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class QuestionRepository constructor(
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val logger: Logger,
) : IQuestionRepository {
    override suspend fun upsert(question: Question): Long {
        return withContext(ioDispatcher) {
            var id = questionDao.upsert(question.asModel())

            id = if (id < 0L) question.id!! else id
            question
                .options
                ?.map { it.asEntity().copy(questionId = id) }
                ?.let {
                    optionDao.insertAll(it)
                }

            id
        }
    }

    override suspend fun upsertMany(question: List<Question>) {
        question.forEach {
            upsert(it)
        }
    }

    override fun getAll(): Flow<List<Question>> {
        return questionDao
            .getAllWithOptsInstTop()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Question?> {
        return questionDao
            .getOneWithOptsInstTop(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override fun getByExamId(examId: Long): Flow<List<Question>> {
        return questionDao
            .getByExamId(examId)
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getByRandom(number: Long): Flow<List<Question>> {
        TODO("Not yet implemented")
//        return questionDao
//            .getByRandom(number)
//            .map { noteEntities ->
//                noteEntities.map {
//                    it.asModel()
//                }
//            }
//            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            questionDao.delete(id)
        }
    }

    override suspend fun deleteOption(id: Long) {
        optionDao.delete(id)
    }
}
