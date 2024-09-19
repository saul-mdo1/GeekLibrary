package com.example.geeklibrary.database.repository.movies

import com.example.geeklibrary.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getAll(): Flow<List<Movie>>
    fun getMovieById(id: Int): Flow<Movie>
    suspend fun insertMovie(movie: Movie)
    suspend fun updateMovie(movie: Movie)
    suspend fun deleteMovie(movieId: Int)
}