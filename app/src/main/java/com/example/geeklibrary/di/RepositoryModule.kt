package com.example.geeklibrary.di

import com.example.geeklibrary.database.dao.*
import com.example.geeklibrary.database.repository.books.BookRepositoryImpl
import com.example.geeklibrary.database.repository.books.BooksRepository
import com.example.geeklibrary.database.repository.movies.MovieRepository
import com.example.geeklibrary.database.repository.movies.MovieRepositoryImpl
import com.example.geeklibrary.database.repository.series.SeriesRepository
import com.example.geeklibrary.database.repository.series.SeriesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBooksRepository(booksDao: BooksDao): BooksRepository {
        return BookRepositoryImpl(booksDao)
    }

    @Provides
    fun provideMovieRepository(movieDao: MoviesDao): MovieRepository {
        return MovieRepositoryImpl(movieDao)
    }

    @Provides
    fun provideSeriesRepository(seriesDao: SeriesDao): SeriesRepository {
        return SeriesRepositoryImpl(seriesDao)
    }
}