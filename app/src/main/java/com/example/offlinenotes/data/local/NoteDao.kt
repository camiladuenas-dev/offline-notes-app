package com.example.offlinenotes.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE isArchived = 0")
    fun getUnarchivedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isFavorite = 1")
    fun getFavoriteNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?
}



