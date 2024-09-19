package com.example.geeklibrary.database.repository.series

import com.example.geeklibrary.model.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getAll(): Flow<List<Series>>
    fun getSeriesById(id: Int): Flow<Series>
    suspend fun insertSeries(serie: Series)
    suspend fun updateSeries(serie: Series)
    suspend fun deleteSeries(serieId: Int)
}