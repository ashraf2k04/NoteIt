package com.ashraf.notes.ui.notes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.notes.components.*
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

    var hasChanges by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId != -1L) {
            val (t, text, hl) = viewModel.loadNote(noteId)
            note = note.copy(title = t, text = text, highlightColor = hl)
            textFieldValue = TextFieldValue(text)
            actualNoteId = noteId
            hasChanges = false
        }
    }

    BackHandler {
        if (hasChanges) showExitDialog = true
        else navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp)
    ) {

        // 🔝 Animated Top Bar
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically { -it / 2 },
            exit = fadeOut() + slideOutVertically { -it / 2 }
        ) {
            EditorTopRow(
                onBack = {
                    if (hasChanges) showExitDialog = true
                    else navController.popBackStack()
                },
                onSave = {
                    viewModel.saveNoteAsync(
                        actualNoteId,
                        note.title,
                        note.text,
                        note.highlightColor
                    ) {
                        hasChanges = false
                        navController.popBackStack()
                    }
                }
            )
        }

        TitleEditor(
            title = note.title,
            onTitleChange = {
                note = note.copy(title = it)
                hasChanges = true
            },
            onSave = {}
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
                hasChanges = true
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )

        // 🔻 Animated Bottom Toolbar
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it }
        ) {
            KeyboardAwareBottomBar(Modifier) {
                NoteEditorToolbar(
                    onIncreaseFont = {
                        note = note.copy(fontSizeSp = (note.fontSizeSp + 2f).coerceAtMost(40f))
                        hasChanges = true
                    },
                    onDecreaseFont = {
                        note = note.copy(fontSizeSp = (note.fontSizeSp - 2f).coerceAtLeast(12f))
                        hasChanges = true
                    },
                    onBold = {
                        note = note.copy(bold = !note.bold)
                        hasChanges = true
                    },
                    onItalic = {
                        note = note.copy(italic = !note.italic)
                        hasChanges = true
                    },
                    onUnderline = {
                        note = note.copy(underline = !note.underline)
                        hasChanges = true
                    },
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
                        hasChanges = true
                    }
                )
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Unsaved changes") },
            text = { Text("Do you want to save your changes before leaving?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.saveNoteAsync(
                            actualNoteId,
                            note.title,
                            note.text,
                            note.highlightColor
                        ) {
                            hasChanges = false
                            showExitDialog = false
                            navController.popBackStack()
                        }
                    }
                ) { Text("Save") }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = {
                            showExitDialog = false
                            navController.popBackStack()
                        }
                    ) { Text("Discard") }

                    TextButton(onClick = { showExitDialog = false }) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}
