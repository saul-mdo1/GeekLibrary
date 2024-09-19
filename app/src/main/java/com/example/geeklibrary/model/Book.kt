package com.example.geeklibrary.model

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val pageNumber: Int,
    val startDate: String,
    val endDate: String?,
    val rate: Float,
    val isFavorite: Boolean,
    val currentReading: Boolean
)
