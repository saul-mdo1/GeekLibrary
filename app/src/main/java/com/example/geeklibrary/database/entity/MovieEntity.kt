package com.example.geeklibrary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.geeklibrary.model.Movie

@Entity(tableName = "Movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String,
    @ColumnInfo val rate: Float,
    @ColumnInfo val isFavorite: Boolean
)
