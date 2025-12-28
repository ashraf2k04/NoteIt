package com.ashraf.notes.ui.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.components.GlassCard
import com.ashraf.notes.ui.components.GlassyBackground
import com.ashraf.notes.ui.navigation.Routes

@Composable
fun NotesScreen(
    navController: androidx.navigation.NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
                        color = Color.White,
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    GlassCard(
                        modifier = Modifier
                            .padding(24.dp)
                            .clickable {
                                navController.navigate(Routes.EditNote.create(-1))
                            }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Create your first note",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Tap to start writing",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(notes) { note ->
                        val isSelected = selectedIds.contains(note.id)

                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .padding(bottom = 12.dp)
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
                            Column {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = note.text
                                        .replace("\\s+".toRegex(), " ")
                                        .take(30) + "...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // 🗑 DELETE CONFIRMATION DIALOG
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
