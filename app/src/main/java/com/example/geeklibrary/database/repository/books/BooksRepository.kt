package com.example.geeklibrary.database.repository.books

import com.example.geeklibrary.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getAll(): Flow<List<Book>>
    fun getBookById(id: Int): Flow<Book>
    suspend fun insertBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(bookId: Int)
}