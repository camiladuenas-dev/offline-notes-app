package com.example.offlinenotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinenotes.data.local.Note
import com.example.offlinenotes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

    val favoriteNotes: StateFlow<List<Note>> = notes
        .map { it.filter { note -> note.isFavorite } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val unarchivedNotes: StateFlow<List<Note>> = notes
        .map { it.filter { note -> !note.isArchived } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            repository.allNotes.collectLatest { noteList ->
                _notes.value = noteList
            }
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _selectedNote.value = repository.getNoteById(id)
        }
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun toggleFavorite(note: Note) {
        viewModelScope.launch {
            repository.update(note.copy(isFavorite = !note.isFavorite))
        }
    }

    fun toggleArchive(note: Note) {
        viewModelScope.launch {
            repository.update(note.copy(isArchived = !note.isArchived))
        }
    }
}
