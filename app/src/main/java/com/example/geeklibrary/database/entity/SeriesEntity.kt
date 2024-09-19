package com.example.geeklibrary.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.geeklibrary.model.Series

@Entity(tableName = "Series")
data class SeriesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val seasonNum: String,
    @ColumnInfo val startDate: String,
    @ColumnInfo val endDate: String?,
    @ColumnInfo val rate: Float,
    @ColumnInfo val isFavorite: Boolean,
    @ColumnInfo val currentWatching: Boolean
)
