package com.example.noteapp.data

import androidx.room3.Delete
import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow


//Data Access Object Dao
@Dao
interface  NoteDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertNote(note : Note)

    //delete
@Delete
suspend fun  delete(note: Note)
    //update
@Update
suspend fun  update(note: Note)
    //get
    @Query("SELECT * FROM notes ORDER BY time DESC")
    fun getAllNotes(): Flow<List<Note>>



}