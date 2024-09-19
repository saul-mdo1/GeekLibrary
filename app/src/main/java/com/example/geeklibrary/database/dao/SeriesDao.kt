package com.example.geeklibrary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.geeklibrary.database.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Query("SELECT * FROM Series")
    fun getAll(): Flow<List<SeriesEntity>>

    @Query("SELECT * FROM Series where id=:id")
    fun getSeriesById(id: Int): Flow<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: SeriesEntity)

    @Update
    fun updateSeries(series: SeriesEntity)

    @Query("DELETE FROM Series WHERE id = :serieId")
    suspend fun deleteById(serieId: Int) //CHECK IF I HAVE TO REMOVE THE SUSPEND
}