package com.example.geeklibrary.di

import android.content.Context
import androidx.room.Room
import com.example.geeklibrary.database.GeekLibraryDatabase
import com.example.geeklibrary.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): GeekLibraryDatabase {
        return Room.databaseBuilder(
            appContext,
            GeekLibraryDatabase::class.java,
            "geek-library-database"
        ).build()
    }

    @Provides
    fun provideBookDao(database: GeekLibraryDatabase): BooksDao = database.booksDao()

    @Provides
    fun provideMovieDao(database: GeekLibraryDatabase): MoviesDao = database.movieDao()

    @Provides
    fun provideSeriesDao(database: GeekLibraryDatabase): SeriesDao = database.seriesDao()
}