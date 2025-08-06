package com.example.offlinenotes.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.offlinenotes.viewmodel.NoteViewModel
import com.example.offlinenotes.data.local.Note
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Unarchive



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    viewModel: NoteViewModel,
    onAddNote: () -> Unit,
    onEditNote: (Int) -> Unit
) {
    val notes = viewModel.notes.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Notas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Text("+")
            }
        }
    ) { padding ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay notas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onClick = { onEditNote(note.id) },
                        onFavoriteClick = { viewModel.toggleFavorite(note) },
                        onArchiveClick = { viewModel.toggleArchive(note) }
                    )

                }
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onArchiveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito"
                    )
                }
                IconButton(onClick = onArchiveClick) {
                    Icon(
                        imageVector = if (note.isArchived) Icons.Default.Archive else Icons.Default.Unarchive,
                        contentDescription = "Archivar"
                    )
                }
            }
        }
    }
}
