package com.ashraf.notes.ui.notes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashraf.notes.ui.components.EditorTopRow
import com.ashraf.notes.ui.components.TitleEditor
import com.ashraf.notes.ui.notes.components.*
import com.ashraf.notes.ui.notes.helpers.NoteString
import com.ashraf.notes.ui.notes.helpers.applyHighlight
import com.ashraf.notes.ui.components.DeleteAlert

@Composable
fun EditNoteScreen(
    noteId: Long,
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    var note by remember {
        mutableStateOf(NoteString(title = "", text = ""))
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
            .padding(WindowInsets.ime.union(WindowInsets.systemBars).asPaddingValues())
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


        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it }
        ) {
            NoteEditorToolbar(
                modifier = Modifier.fillMaxWidth(),
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

    if (showExitDialog) {

        DeleteAlert(
            onDismiss = { showExitDialog = false },
            onSave = {
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
            },
            onDiscard ={
                showExitDialog = false
                navController.popBackStack()
            },
        )
    }
}
