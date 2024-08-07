package com.mshdabiola.data.repository

import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.asModel
import com.mshdabiola.seriesdatabase.dao.TopicDao
import com.mshdabiola.seriesmodel.Topic
import com.mshdabiola.seriesmodel.TopicWithCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class TopicRepository(
    private val topicDao: TopicDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ITopicRepository {
    override suspend fun upsert(topic: Topic): Long {
        return withContext(ioDispatcher) {
            topicDao.upsert(topic.asEntity())
        }
    }

    override suspend fun insertAll(topic: List<Topic>) {
        return withContext(ioDispatcher) {
            topicDao.insertAll(topic.map { it.asEntity() })
        }
    }

    override fun getAll(): Flow<List<Topic>> {
        return topicDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllBySubject(subjectID: Long): Flow<List<Topic>> {
        return topicDao
            .getAllByCategory(subjectID)
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Topic?> {
        return topicDao
            .getOne(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override fun getOneWithCategory(id: Long): Flow<TopicWithCategory?> {
        return topicDao
            .getOneWithCategory(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            topicDao.delete(id)
        }
    }
}
