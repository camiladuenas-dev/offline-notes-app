package com.example.offlinenotes.data.repository

import com.example.offlinenotes.data.local.Note
import com.example.offlinenotes.data.local.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    override val unarchivedNotes: Flow<List<Note>> = noteDao.getUnarchivedNotes()

    override suspend fun insert(note: Note) = noteDao.insertNote(note)

    override suspend fun update(note: Note) = noteDao.updateNote(note)

    override suspend fun delete(note: Note) = noteDao.deleteNote(note)

    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)

}
