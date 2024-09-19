package com.example.geeklibrary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val author: String,
    @ColumnInfo val pageNumber: Int,
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String?,
    @ColumnInfo val rate: Float,
    @ColumnInfo val isFavorite: Boolean,
    @ColumnInfo val currentReading: Boolean
)
