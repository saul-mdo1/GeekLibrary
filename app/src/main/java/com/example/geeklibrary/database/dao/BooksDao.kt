package com.example.geeklibrary.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.geeklibrary.database.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Query("SELECT * FROM Book")
    fun getAll(): Flow<List<BookEntity>>

    @Query("SELECT * FROM Book where id=:id")
    fun getBookById(id: Int): Flow<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookEntity)

    @Update
    fun updateBook(book: BookEntity)

    @Query("DELETE FROM Book WHERE id = :bookId")
    suspend fun deleteById(bookId: Int)
}