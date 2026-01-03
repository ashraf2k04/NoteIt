package com.ashraf.notes.ui.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.common.UiState
import com.ashraf.notes.ui.components.GlassCard
import com.ashraf.notes.ui.components.GlassyBackground
import com.ashraf.notes.ui.components.SwipeToDismiss
import com.ashraf.notes.ui.navigation.Routes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: androidx.navigation.NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.notesState.collectAsState()

    var selectionMode by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    fun toggleSelection(id: Long) {
        selectedIds =
            if (selectedIds.contains(id)) selectedIds - id
            else selectedIds + id

        if (selectedIds.isEmpty()) selectionMode = false
    }

    GlassyBackground {
        when (state) {
            UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        (state as UiState.Error).message,
                    )
                }
            }

            is UiState.Success -> {
                val notes = (state as UiState.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {

                    // 🔝 Selection header
                    if (selectionMode) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${selectedIds.size} selected",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Delete",
                                color = Color.Red,
                                modifier = Modifier.clickable {
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }

                    if (notes.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            GlassCard(
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        Routes.EditNote.create(-1)
                                    )
                                }
                            ) {
                                Text("Create your first note", color = Color.White)
                            }
                        }
                    } else {
                        LazyColumn {
                            items(notes, key = { it.id }) { note ->
                                val isSelected = selectedIds.contains(note.id)

                                SwipeToDismiss(
                                    isSelected = isSelected,
                                    onSwipedLeft = {
                                        // ✅ LEFT SWIPE → ENTER SELECTION MODE + SELECT NOTE
                                        if (!selectionMode) {
                                            selectionMode = true
                                        }
                                        toggleSelection(note.id)
                                    },
                                    onSwipedRight = {
                                        // ❌ RIGHT SWIPE BLOCKED (NO ACTION)
                                    }
                                ) {
                                    GlassCard(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .combinedClickable(
                                                onClick = {
                                                    if (selectionMode) {
                                                        toggleSelection(note.id)
                                                    } else {
                                                        navController.navigate(
                                                            Routes.EditNote.create(note.id)
                                                        )
                                                    }
                                                },
                                                onLongClick = {
                                                    selectionMode = true
                                                    toggleSelection(note.id)
                                                }
                                            ),
                                        selected = isSelected
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(start = 10.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = note.title,
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                text = note.text.take(30) + "...",
                                                )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 🗑 DELETE CONFIRMATION
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteNotes(selectedIds.toList())
                        selectedIds = emptySet()
                        selectionMode = false
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            text = { Text("Delete selected notes?") }
        )
    }
}
