package com.example.geeklibrary.model

data class Movie(
    val id: Int = 0,
    val name: String,
    val startDate: String,
    val endDate: String,
    val rate: Float,
    val isFavorite: Boolean
)
