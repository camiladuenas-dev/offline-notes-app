package com.example.offlinenotes.presentation.navigation

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object AddEditNote : Screen("add_edit_note")
}
