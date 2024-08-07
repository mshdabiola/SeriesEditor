package com.mshdabiola.data.repository

import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.asModel
import com.mshdabiola.seriesdatabase.dao.TopicCategoryDao
import com.mshdabiola.seriesmodel.TopicCategory
import com.mshdabiola.seriesmodel.TopicWithCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TopicCategoryRepository(
    private val topicCategoryDao: TopicCategoryDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ITopicCategory {

    override fun getAll(subjectId: Long): Flow<List<TopicCategory>> {
        return topicCategoryDao
            .getAllBySubjectId(subjectId)
            .map { entities -> entities.map { it.asModel() } }
            .flowOn(ioDispatcher)
    }

    override fun getAll(): Flow<List<TopicCategory>> {
        return topicCategoryDao
            .getAll()
            .map { entities -> entities.map { it.asModel() } }
            .flowOn(ioDispatcher)
    }

    override fun getTopicBySubject(subjectId: Long): Flow<List<TopicWithCategory>> {
        return topicCategoryDao
            .getAllWithTopicsBySubjectId(subjectId)
            .map { withTopics -> withTopics.map { it.asModel() }.flatten() }
            .flowOn(ioDispatcher)
    }

    override fun getCategories(subjectId: Long): Flow<List<TopicCategory>> {
        return topicCategoryDao
            .getAllBySubjectId(subjectId)
            .map { entities -> entities.map { it.asModel() } }
            .flowOn(ioDispatcher)
    }

    override suspend fun upsert(topicCategory: TopicCategory): Long {
        return withContext(ioDispatcher) {
            topicCategoryDao.upsert(topicCategory.asEntity())
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            topicCategoryDao.delete(id)
        }
    }
}
