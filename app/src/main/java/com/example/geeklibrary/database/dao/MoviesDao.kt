package com.example.geeklibrary.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.geeklibrary.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM Movie where id=:id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("DELETE FROM Movie WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)
}