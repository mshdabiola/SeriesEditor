package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.ISeriesRepository
import com.mshdabiola.database.asEntity
import com.mshdabiola.database.asModel
import com.mshdabiola.database.dao.SeriesDao
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.testing.defaultData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FakeSeriesRepository: ISeriesRepository {
    private val _series = MutableStateFlow(defaultData.series)

    override fun getAll(): Flow<List<Series>> {
       return _series
    }

    override fun getOne(id: Long): Flow<Series?> {
        return _series
            .map { it.firstOrNull { it.id == id } }
    }

    override suspend fun upsert(series: Series): Long {
       _series.value = _series.value.toMutableList().apply {
           if (series.id == -1L) {
               add(series)
           }else{
               val index = this.indexOfFirst { it.id == series.id }
               add(index, series)
           }
       }
        return 1
    }

    override suspend fun delete(id: Long) {
         _series.value = _series.value.toMutableList().apply {
            removeIf { it.id == id }
        }
    }

}
