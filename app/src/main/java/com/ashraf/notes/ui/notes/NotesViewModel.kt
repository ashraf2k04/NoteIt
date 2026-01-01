package com.ashraf.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashraf.notes.data.local.note.NoteDao
import com.ashraf.notes.data.local.note.NoteEntity
import com.ashraf.notes.ui.common.UiState
import com.ashraf.notes.ui.notes.helpers.HighlightRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    val notesState: StateFlow<UiState<List<NoteEntity>>> =
        noteDao.getNotes()
            .map<List<NoteEntity>, UiState<List<NoteEntity>>> {
                UiState.Success(it)
            }
            .catch { emit(UiState.Error("Failed to load notes")) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                UiState.Loading
            )

    fun saveNoteAsync(
        noteId: Long,
        title: String,
        text: String,
        highlights: List<HighlightRange>,
        onResult: (Long) -> Unit = {}
    ) {
        viewModelScope.launch {
            val id = saveNote(noteId, title, text, highlights)
            onResult(id)
        }
    }

    suspend fun saveNote(
        noteId: Long,
        title: String,
        text: String,
        highlights: List<HighlightRange>
    ): Long {
        if (title.isBlank() && text.isBlank()) return noteId

        return noteDao.insert(
            NoteEntity(
                id = if (noteId == -1L) 0 else noteId,
                title = title.ifBlank { "Untitled" },
                text = text,
                highlightsJson = Json.encodeToString(highlights)
            )
        )
    }

    suspend fun loadNote(noteId: Long): Triple<String, String, List<HighlightRange>> {
        val note = noteDao.getNoteById(noteId)
            ?: return Triple("", "", emptyList())

        val highlights = try {
            Json.decodeFromString<List<HighlightRange>>(note.highlightsJson)
        } catch (_: Exception) {
            emptyList()
        }

        return Triple(note.title, note.text, highlights)
    }

    fun deleteNotes(ids: List<Long>) {
        viewModelScope.launch {
            noteDao.deleteNotes(ids)
        }
    }
}
