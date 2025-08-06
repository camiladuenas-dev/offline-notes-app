package com.example.offlinenotes.di

import android.app.Application
import androidx.room.Room
import com.example.offlinenotes.data.local.NoteDao
import com.example.offlinenotes.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_db"
        )
            .fallbackToDestructiveMigration() // 💥 Esta línea evita el error al cambiar el esquema
            .build()
    }
    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao
    }

}
