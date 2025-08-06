package com.example.offlinenotes.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.offlinenotes.ui.screens.AddEditNoteScreen
import com.example.offlinenotes.presentation.ui.screens.NotesListScreen
import com.example.offlinenotes.viewmodel.NoteViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route,
    ) {
        composable(route = Screen.NotesList.route) { backStackEntry: NavBackStackEntry ->
            val viewModel: NoteViewModel = hiltViewModel(backStackEntry)


            NotesListScreen(
                viewModel = viewModel,
                onAddNote = {
                    navController.navigate(Screen.AddEditNote.route)
                },
                onEditNote = { noteId ->
                    navController.navigate("${Screen.AddEditNote.route}?noteId=$noteId")
                }
            )
        }

        composable(
            route = "${Screen.AddEditNote.route}?noteId={noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            val viewModel: NoteViewModel = hiltViewModel(backStackEntry)

            AddEditNoteScreen(
                selectedNote = viewModel.selectedNote.value,
                noteId = noteId,
                onNoteSaved = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
    }
}
