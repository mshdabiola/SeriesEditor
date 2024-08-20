package com.mshdabiola.data.repository

import com.mshdabiola.seriesmodel.Series
import kotlinx.coroutines.flow.Flow

interface ISeriesRepository {

    fun getAll(): Flow<List<Series>>

    fun getOne(id: Long): Flow<Series?>

    suspend fun upsert(series: Series): Long

    suspend fun delete(id: Long)
}
