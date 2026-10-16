package com.example.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel (private val noteRepository : NoteRepository): ViewModel(){


    val allNotes: Flow<List<Note>> = noteRepository.getAllNote.map { notes ->
        notes.sortedByDescending { note-> note.time }

    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000,),emptyList()
    )


    fun addNote(note:Note){
        viewModelScope.launch {

        noteRepository.insertNote(note)
        }
    }


    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun updateNote(note :Note){
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

}