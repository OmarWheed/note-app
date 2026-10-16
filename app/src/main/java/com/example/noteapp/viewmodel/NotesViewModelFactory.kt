package com.example.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.data.NoteRepository

class NotesViewModelFactory(
    private  val repository : NoteRepository
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository)as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}