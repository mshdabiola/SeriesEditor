package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.TopicWithCategory
import com.mshdabiola.testing.exportableData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeTopicRepository : ITopicRepository {
    private val _topic = MutableStateFlow<List<Topic>>(exportableData.topics)

    override suspend fun upsert(topic: Topic): Long {
        _topic.value = _topic.value.toMutableList().apply {
            if (topic.id == -1L) {
                add(topic)
            } else {
                val index = this.indexOfFirst { it.id == topic.id }
                add(index, topic)
            }
        }
        return 1
    }

    override suspend fun insertAll(topic: List<Topic>) {
        _topic.value = _topic.value.toMutableList().apply {
            addAll(topic)
        }
    }

    override fun getAll(): Flow<List<Topic>> {
        return _topic
    }

    override fun getAllBySubject(subjectID: Long): Flow<List<Topic>> {
        return _topic
    }

    override fun getOne(id: Long): Flow<Topic?> {
        return _topic
            .map { it.firstOrNull { it.id == id } }
    }

    override fun getOneWithCategory(id: Long): Flow<TopicWithCategory?> {
        return _topic
            .map { it.firstOrNull { it.id == id } }
            .map { topic ->
                topic?.let {
                    val category = exportableData.topicCategory.first { it.id == topic.categoryId }
                    TopicWithCategory(topic.id, category, topic.title)
                }
            }
    }

    override suspend fun delete(id: Long) {
        _topic.value = _topic.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }
}
