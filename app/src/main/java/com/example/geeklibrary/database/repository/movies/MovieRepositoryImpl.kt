package com.example.geeklibrary.database.repository.movies

import com.example.geeklibrary.database.dao.MoviesDao
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.utils.toEntity
import com.example.geeklibrary.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(private val moviesDao: MoviesDao) : MovieRepository {

    override fun getAll(): Flow<List<Movie>> {
        return moviesDao.getAll().map {
            it.map { movieEntity ->
                movieEntity.toModel()
            }
        }
    }

    override fun getMovieById(id: Int): Flow<Movie> {
        return moviesDao.getMovieById(id).map {
            it.toModel()
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        moviesDao.insertMovie(movie.toEntity())
    }

    override suspend fun updateMovie(movie: Movie) {
        moviesDao.updateMovie(movie.toEntity())
    }

    override suspend fun deleteMovie(movieId: Int) {
        moviesDao.deleteById(movieId)
    }
}