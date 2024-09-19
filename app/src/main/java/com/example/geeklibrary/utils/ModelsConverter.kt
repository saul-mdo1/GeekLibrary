package com.example.geeklibrary.utils

import com.example.geeklibrary.database.entity.BookEntity
import com.example.geeklibrary.database.entity.MovieEntity
import com.example.geeklibrary.database.entity.SeriesEntity
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.model.Movie
import com.example.geeklibrary.model.Series


fun BookEntity.toModel() = Book(
    id = id,
    title = title,
    author = author,
    pageNumber = pageNumber,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite,
    currentReading = currentReading
)


fun Book.toEntity() = BookEntity(
    id = id,
    title = title,
    author = author,
    pageNumber = pageNumber,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite,
    currentReading = currentReading
)

fun MovieEntity.toModel() = Movie(
    id = id,
    name = name,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite
)

fun Movie.toEntity() = MovieEntity(
    id = id,
    name = name,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite
)

fun SeriesEntity.toModel() = Series(
    id = id,
    name = name,
    seasonNum = seasonNum,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite,
    currentWatching = currentWatching
)

fun Series.toEntity() = SeriesEntity(
    id = id,
    name = name,
    seasonNum = seasonNum,
    startDate = startDate,
    endDate = endDate,
    rate = rate,
    isFavorite = isFavorite,
    currentWatching = currentWatching
)