package com.example.geeklibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.geeklibrary.database.dao.*
import com.example.geeklibrary.database.entity.*

@Database(
    entities = [BookEntity::class, MovieEntity::class, SeriesEntity::class],
    version = 1
)
abstract class GeekLibraryDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao

    abstract fun movieDao(): MoviesDao

    abstract fun seriesDao(): SeriesDao
}