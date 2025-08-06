// AddEditNoteScreen.kt
// Autor: Camila Dueñas
// Fecha: 2025-07-25
// Descripción: Pantalla para agregar o editar una nota.

package com.example.offlinenotes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinenotes.data.local.Note
import com.example.offlinenotes.viewmodel.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    selectedNote: Note?,
    viewModel: NoteViewModel,
    noteId: Int,
    onNoteSaved: () -> Unit
) {
    val selectedNote = viewModel.selectedNote.collectAsState().value
    val allNotes = viewModel.notes.collectAsState().value

    LaunchedEffect(noteId) {
        if (noteId != -1) viewModel.getNoteById(noteId)
        else viewModel.clearSelectedNote()
    }

    if (noteId != -1 && selectedNote == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isArchived by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(selectedNote) {
        selectedNote?.let {
            title = it.title
            content = it.content
            isArchived = it.isArchived
            isFavorite = it.isFavorite
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (selectedNote != null) "Editar nota" else "Nueva nota")
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val duplicate = allNotes.any {
                    it.title.trim() == title.trim() && it.id != noteId
                }
                if (duplicate) return@FloatingActionButton

                val note = if (selectedNote != null) {
                    selectedNote.copy(
                        title = title,
                        content = content,
                        isArchived = isArchived,
                        isFavorite = isFavorite
                    )
                } else {
                    Note(
                        title = title,
                        content = content,
                        isArchived = isArchived,
                        isFavorite = isFavorite,
                        timestamp = System.currentTimeMillis(),
                        color = 0xFFE1F5FE.toInt()
                    )
                }

                if (selectedNote != null) viewModel.updateNote(note)
                else viewModel.insertNote(note)

                viewModel.clearSelectedNote()
                onNoteSaved()
            }) {
                Text("💾")
            }
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Contenido") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconToggleButton(checked = isArchived, onCheckedChange = { isArchived = it }) {
                    Icon(
                        imageVector = if (isArchived) Icons.Default.Archive else Icons.Default.Unarchive,
                        contentDescription = "Archivar"
                    )
                }

                IconToggleButton(checked = isFavorite, onCheckedChange = { isFavorite = it }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito"
                    )
                }
            }
        }
    }
}
