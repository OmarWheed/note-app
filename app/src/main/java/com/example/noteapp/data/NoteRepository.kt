package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private  val noteDao: NoteDao){

    val getAllNote: Flow<List<Note>> =noteDao.getAllNotes()

    suspend fun insertNote(note:Note){
        noteDao.insertNote(note)
    }

    suspend fun  deleteNote(note: Note){
        noteDao.delete(note)
    }

    suspend fun  updateNote(note:Note){
        noteDao.update(note)
    }


}