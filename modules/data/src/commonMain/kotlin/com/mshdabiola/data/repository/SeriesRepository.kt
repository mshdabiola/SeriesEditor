package com.mshdabiola.data.repository

import com.mshdabiola.database.dao.SeriesDao
import com.mshdabiola.database.model.asEntity
import com.mshdabiola.database.model.asModel
import com.mshdabiola.generalmodel.Series
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SeriesRepository(
    private val seriesDao: SeriesDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ISeriesRepository {
    override fun getAll(): Flow<List<Series>> {
        return seriesDao.getAll()
            .map { entities -> entities.map { it.asModel() } }
    }

    override fun getOne(id: Long): Flow<Series?> {
        return seriesDao.getOne(id)
            .map { it?.asModel() }
    }

    override suspend fun upsert(series: Series):Long {
       return withContext(ioDispatcher) {
            seriesDao.upsert(series.asEntity())
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            seriesDao.delete(id)
        }
    }
}
