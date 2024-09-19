package com.example.geeklibrary.model

enum class ElementType(id: Int) {
    BOOK(1),
    MOVIE(2),
    SERIE(3);

    companion object {
        fun getTypeById(id: Int?) =
            when (id) {
                1 -> BOOK
                2 -> MOVIE
                else -> SERIE
            }
    }
}