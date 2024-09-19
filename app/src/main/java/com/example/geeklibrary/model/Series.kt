package com.example.geeklibrary.model

data class Series(
    val id: Int = 0,
    val name: String,
    val seasonNum: String,
    val startDate: String,
    val endDate: String?,
    val rate: Float,
    val isFavorite: Boolean,
    val currentWatching: Boolean
)
