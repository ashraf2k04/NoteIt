package com.ashraf.notes.ui.todo

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
import com.ashraf.notes.ui.todo.components.CircularCheckbox
import com.ashraf.notes.ui.todo.helpers.formatDateTime
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoScreen(
    navController: androidx.navigation.NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {

    val state by viewModel.todosState.collectAsState()

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
                    Text((state as UiState.Error).message)
                }
            }

            is UiState.Success -> {
                val todos = (state as UiState.Success).data

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

                    if (todos.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            GlassCard(
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        Routes.EditTodo.create(-1)
                                    )
                                }
                            ) {
                                Text("Create your first todo")
                            }
                        }
                    } else {
                        LazyColumn {
                            items(todos, key = { it.id }) { todo ->
                                val isSelected = selectedIds.contains(todo.id)

                                SwipeToDismiss(
                                    isSelected = isSelected,
                                    onSwipedLeft = {
                                        // ✅ LEFT SWIPE → ENTER SELECTION MODE + SELECT ITEM
                                        if (!selectionMode) {
                                            selectionMode = true
                                        }
                                        toggleSelection(todo.id)
                                    },
                                    onSwipedRight = {
                                        // ✅ RIGHT SWIPE → TOGGLE COMPLETED (ONLY IF NOT SELECTING)
                                        viewModel.toggleCompleted(todo)

                                    },
                                    content = {
                                        GlassCard(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(bottom = 12.dp)
                                                .combinedClickable(
                                                    onClick = {
                                                        if (selectionMode) {
                                                            toggleSelection(todo.id)
                                                        } else {
                                                            navController.navigate(
                                                                Routes.EditTodo.create(todo.id)
                                                            )
                                                        }
                                                    },
                                                    onLongClick = {
                                                        selectionMode = true
                                                        toggleSelection(todo.id)
                                                    }
                                                ),
                                            selected = isSelected
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxHeight(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                CircularCheckbox(
                                                    checked = todo.completed,
                                                    onCheckedChange = {
                                                        viewModel.toggleCompleted(todo)
                                                    }
                                                )

                                                Column(
                                                    modifier = Modifier.padding(start = 10.dp),
                                                    verticalArrangement = Arrangement.SpaceEvenly
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(start = 20.dp),
                                                        text = todo.title,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        color = Color.White
                                                    )

                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(
                                                            12.dp
                                                        )
                                                    ) {
                                                        if (todo.dueDate != null) {
                                                            Text(
                                                                "📅 ${formatDateTime(todo.dueDate)}",
                                                                style = MaterialTheme.typography.bodySmall,

                                                                )
                                                        }

                                                        if (todo.reminderTime != null) {
                                                            Text(
                                                                "⏰ ${formatDateTime(todo.reminderTime)}",
                                                                style = MaterialTheme.typography.bodySmall,

                                                                )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                )
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
                        viewModel.deleteTodos(selectedIds.toList())
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
            text = { Text("Delete selected todos?") }
        )
    }
}