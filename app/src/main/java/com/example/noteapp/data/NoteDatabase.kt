package com.example.noteapp.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class  NoteDatabase : RoomDatabase(){

    abstract  fun noteDao(): NoteDao
    companion object {
        @Volatile
        private  var INSTANCE: NoteDatabase? =null
        fun getDatabase(context : Context): NoteDatabase {
return INSTANCE ?: synchronized(this){
    val instance =Room.databaseBuilder(
        context = context.applicationContext,
        klass = NoteDatabase::class.java,
        name = "notes_db"
    ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}