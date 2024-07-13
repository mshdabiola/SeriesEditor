package com.mshdabiola.data.repository

import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.TopicCategory
import com.mshdabiola.generalmodel.TopicWithCategory
import kotlinx.coroutines.flow.Flow

interface ITopicCategory {

    fun getAll(subjectId: Long): Flow<List<TopicCategory>>
    fun getAll(): Flow<List<TopicCategory>>

    fun getTopicBySubject(subjectId: Long): Flow<List<TopicWithCategory>>

    fun getCategories(subjectId: Long): Flow<List<TopicCategory>>

    suspend fun upsert(topicCategory: TopicCategory):Long

    suspend fun delete(id: Long)

}