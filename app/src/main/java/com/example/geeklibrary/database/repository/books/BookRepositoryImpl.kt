package com.example.geeklibrary.database.repository.books

import com.example.geeklibrary.database.dao.BooksDao
import com.example.geeklibrary.model.Book
import com.example.geeklibrary.utils.toEntity
import com.example.geeklibrary.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BooksDao) : BooksRepository {

    override fun getAll(): Flow<List<Book>> {
        return bookDao.getAll().map {
            it.map { bookEntity ->
                bookEntity.toModel()
            }
        }
    }

    override fun getBookById(id: Int): Flow<Book> {
        return bookDao.getBookById(id).map {
            it.toModel()
        }
    }

    override suspend fun insertBook(book: Book) {
        bookDao.insertBook(book.toEntity())
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book.toEntity())
    }

    override suspend fun deleteBook(bookId: Int) {
        bookDao.deleteById(bookId)
    }
}