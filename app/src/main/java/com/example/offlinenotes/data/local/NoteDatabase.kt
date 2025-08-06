// NoteDatabase.kt
// Autor: Camila Dueñas
// Fecha: 2025-07-25
// Descripción: Clase abstracta que representa la base de datos Room que contiene la entidad Note y su DAO.

package com.example.offlinenotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}

