package com.example.noteapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.noteapp.data.Note
import com.example.noteapp.noteui.AddNoteScreen
import com.example.noteapp.noteui.NoteDetailScreen
import com.example.noteapp.noteui.NotesScreen
import com.example.noteapp.viewmodel.NotesViewModel
import kotlinx.serialization.*

sealed interface NavBarRoute {

    @Serializable
    data object Notes : NavBarRoute
    @Serializable
    data object  Todo : NavBarRoute
}

// Define a home route that doesn't take any arguments
@Serializable

object  AddNote

@Serializable
data class NoteDetails(val id:Int)




@Composable
fun AppNav(notesViewModel: NotesViewModel) {
    val navController = rememberNavController()
    val notes = notesViewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList()).value

    NavHost(
        navController = navController,
        startDestination = NavBarRoute.Notes
    ) {
        composable<NavBarRoute.Notes> {
            NotesScreen(
                notes = notes,
                onAddNoteClick = { navController.navigate(AddNote) },
                onNoteClick = { note -> navController.navigate(NoteDetails(note.id)) }
            )
        }

        composable<AddNote> {
            AddNoteScreen(
                onSaveNote = { note ->
                    notesViewModel.addNote(note)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable<NoteDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<NoteDetails>()
            val note = notes.firstOrNull { it.id == route.id }

            if (note != null) {
                NoteDetailScreen(
                    note = note,
                    onBack = { navController.popBackStack() },
                    onDelete = { selectedNote ->
                        notesViewModel.deleteNote(selectedNote)
                        navController.popBackStack()
                    },
                    onSave = notesViewModel::updateNote
                )
            } else {
                LaunchedEffect(route.id, notes) {
                    if (notes.isNotEmpty()) navController.popBackStack()
                }
            }
        }

    }
}