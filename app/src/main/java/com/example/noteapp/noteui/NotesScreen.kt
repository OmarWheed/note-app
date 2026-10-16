package com.example.noteapp.noteui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import com.example.noteapp.data.Note
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notes:List<Note>,
    onAddNoteClick:()-> Unit,
    onNoteClick:(Note)-> Unit
){
    var showDialog by remember{ mutableStateOf(false) }
    Scaffold(
        topBar = {
             NoteTopBar(
         onSearchClicked = {},
                 onMenuClicked ={showDialog = true}
         )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(Icons.AutoMirrored.Outlined.NoteAdd, contentDescription = "Add note")
            }
        },

    ) { innerPadding ->

        if (notes.isEmpty()) {
            AnimatedEmptyState(modifier = Modifier.padding(innerPadding).fillMaxSize())
        } else {



                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = notes, key = { note -> note.id }) { note ->
                        NoteCard(note = note, onClick = { onNoteClick(note) })

                    }
                }

            }



        if(showDialog){
            BasicAlertDialog(
                onDismissRequest = {
showDialog=false
                },
                content = {
                    Row {
                        Text("About App")
                        Text("by Omar Waheed Mokter awad ")
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            onClick = {
                                showDialog=false
                            }
                        )  {
                            Text("Ok")
                        }


                    }
                }
            )
        }



    }
}
