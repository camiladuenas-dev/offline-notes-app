// Ruta: data/repository/NoteRepository.kt
package com.example.offlinenotes.data.repository

import com.example.offlinenotes.data.local.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    val allNotes: Flow<List<Note>>
    val unarchivedNotes: Flow<List<Note>>

    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
    suspend fun getNoteById(id: Int): Note?

}

