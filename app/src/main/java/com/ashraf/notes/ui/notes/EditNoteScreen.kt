package com.ashraf.notes.ui.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.notes.components.EditorTopRow
import com.ashraf.notes.ui.notes.components.HighlightableTextField
import com.ashraf.notes.ui.notes.components.KeyboardAwareBottomBar
import com.ashraf.notes.ui.notes.components.NoteEditorToolbar
import com.ashraf.notes.ui.notes.components.TitleEditor
import com.ashraf.notes.ui.notes.helpers.NoteString
import com.ashraf.notes.ui.notes.helpers.applyHighlight

@Composable
fun EditNoteScreen(
    noteId: Long,
    initialTitle: String,
    navController: androidx.navigation.NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    var note by remember {
        mutableStateOf(NoteString(title = initialTitle, text = ""))
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    var actualNoteId by remember { mutableLongStateOf(noteId) }


    LaunchedEffect(noteId) {
        if (noteId != -1L) {
            val (t, text, hl) = viewModel.loadNote(noteId)
            note = note.copy(title = t, text = text, highlightColor = hl)
            textFieldValue = TextFieldValue(text)
            actualNoteId = noteId
        }
    }

    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top =20.dp, bottom = 20.dp)
        ) {

            EditorTopRow(
                onBack = { navController.popBackStack() },
                onSave = {
                    viewModel.saveNoteAsync(
                        actualNoteId,
                        note.title,
                        note.text,
                        note.highlightColor
                    ) { newId ->
                        actualNoteId = newId
                        navController.popBackStack()
                    }
                }
            )


            TitleEditor(
                title = note.title,
                onTitleChange = { note = note.copy(title = it) },
                onSave = {
                    if (actualNoteId != -1L) {
                        viewModel.saveNoteAsync(
                            actualNoteId,
                            note.title,
                            note.text,
                            note.highlightColor
                        ) { newId ->
                            actualNoteId = newId
                        }
                    }
                }
            )

            HighlightableTextField(
                value = textFieldValue,
                note = note,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    note = note.copy(
                        text = newValue.text,
                        highlightColor = note.highlightColor.filter {
                            it.end <= newValue.text.length && it.start < it.end
                        }
                    )
                },
                modifier = Modifier.fillMaxSize().weight(1f)
            )




            KeyboardAwareBottomBar(Modifier) {
                NoteEditorToolbar(
                    onIncreaseFont = {
                        note = note.copy(fontSizeSp = (note.fontSizeSp + 2f).coerceAtMost(40f))
                    },
                    onDecreaseFont = {
                        note = note.copy(fontSizeSp = (note.fontSizeSp - 2f).coerceAtLeast(12f))
                    },
                    onBold = { note = note.copy(bold = !note.bold) },
                    onItalic = { note = note.copy(italic = !note.italic) },
                    onUnderline = { note = note.copy(underline = !note.underline) },
                    onHighlight = { color ->
                        val sel = textFieldValue.selection
                        note = note.copy(
                            highlightColor = applyHighlight(
                                sel.start,
                                sel.end,
                                color,
                                note.highlightColor
                            )
                        )
                    }
                )
            }
        }
    }
}