package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.data.NoteDatabase
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.navigation.AppNav
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewmodel.NotesViewModel
import com.example.noteapp.viewmodel.NotesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(database.noteDao())
        val notesViewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(repository)
        )[NotesViewModel::class.java]

        setContent {
            NoteAppTheme {
                AppNav(notesViewModel)
            }
        }
    }
}

