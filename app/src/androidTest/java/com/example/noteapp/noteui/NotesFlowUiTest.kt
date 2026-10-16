package com.example.noteapp.noteui

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.noteapp.data.Note
import com.example.noteapp.ui.theme.NoteAppTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NotesFlowUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun notesScreenAddAndNoteCallbacksAreInvoked() {
        var addClicked = false
        var openedNote: Note? = null
        val note = Note(id = 7, title = "Meeting", content = "Bring notes")

        composeRule.setContent {
            NoteAppTheme {
                NotesScreen(
                    notes = listOf(note),
                    onAddNoteClick = { addClicked = true },
                    onNoteClick = { openedNote = it }
                )
            }
        }

        composeRule.onNodeWithContentDescription("Add note").performClick()
        composeRule.onNode(hasClickAction() and hasText("Meeting")).performClick()

        composeRule.runOnIdle {
            assertEquals(true, addClicked)
            assertEquals(note, openedNote)
        }
    }

    @Test
    fun addNoteSavesTitleAndContentSeparately() {
        var savedNote: Note? = null

        composeRule.setContent {
            NoteAppTheme {
                AddNoteScreen(onSaveNote = { savedNote = it })
            }
        }

        composeRule.onNodeWithText("Title").performTextInput("Shopping")
        composeRule.onNodeWithText("Write your note ...").performTextInput("Milk and bread")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.runOnIdle {
            assertEquals("Shopping", savedNote?.title)
            assertEquals("Milk and bread", savedNote?.content)
        }
    }
}
