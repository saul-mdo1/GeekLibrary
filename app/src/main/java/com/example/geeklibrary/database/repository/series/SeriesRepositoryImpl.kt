package com.example.geeklibrary.database.repository.series

import com.example.geeklibrary.database.dao.SeriesDao
import com.example.geeklibrary.model.Series
import com.example.geeklibrary.utils.toEntity
import com.example.geeklibrary.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SeriesRepositoryImpl(private val seriesDao: SeriesDao) :
    SeriesRepository {

    override fun getAll(): Flow<List<Series>> {
        return seriesDao.getAll().map {
            it.map { serieEntity ->
                serieEntity.toModel()
            }
        }
    }

    override fun getSeriesById(id: Int): Flow<Series> {
        return seriesDao.getSeriesById(id).map {
            it.toModel()
        }
    }

    override suspend fun insertSeries(serie: Series) {
        seriesDao.insertSeries(serie.toEntity())
    }

    override suspend fun updateSeries(serie: Series) {
        seriesDao.updateSeries(serie.toEntity())
    }

    override suspend fun deleteSeries(serieId: Int) {
        seriesDao.deleteById(serieId)
    }
}