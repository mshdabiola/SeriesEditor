package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.ITopicCategory
import com.mshdabiola.seriesmodel.TopicCategory
import com.mshdabiola.seriesmodel.TopicWithCategory
import com.mshdabiola.testing.exportableData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTopicCategoryRepository : ITopicCategory {

    private val _topicCategory = MutableStateFlow<List<TopicCategory>>(exportableData.topicCategory)

    override fun getAll(subjectId: Long): Flow<List<TopicCategory>> {
        return _topicCategory
            .map { it.filter { it.subjectId == subjectId } }
    }

    override fun getAll(): Flow<List<TopicCategory>> {
        return _topicCategory
    }

    override fun getTopicBySubject(subjectId: Long): Flow<List<TopicWithCategory>> {
        return _topicCategory
            .map { it.filter { it.subjectId == subjectId } }
            .map {
                it.map { topicCategory ->
                    val topic = exportableData.topics.first { it.categoryId == it.id }
                    TopicWithCategory(topic.id, topicCategory, topic.title)
                }
            }
    }

    override fun getCategories(subjectId: Long): Flow<List<TopicCategory>> {
        return _topicCategory
            .map { it.filter { it.subjectId == subjectId } }
    }

    override suspend fun upsert(topicCategory: TopicCategory): Long {
        _topicCategory.value = _topicCategory.value.toMutableList().apply {
            if (topicCategory.id == -1L) {
                add(topicCategory)
            } else {
                val index = this.indexOfFirst { it.id == topicCategory.id }
                add(index, topicCategory)
            }
        }
        return 1
    }

    override suspend fun delete(id: Long) {
        _topicCategory.value = _topicCategory.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }
}
