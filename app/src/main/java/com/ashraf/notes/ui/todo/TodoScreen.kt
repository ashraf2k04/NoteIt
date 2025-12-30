package com.ashraf.notes.ui.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.components.GlassCard
import com.ashraf.notes.ui.components.GlassyBackground
import com.ashraf.notes.ui.components.SwipeToDismiss
import com.ashraf.notes.ui.navigation.Routes
import com.ashraf.notes.ui.todo.helpers.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    navController: androidx.navigation.NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState()

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

            // 🔝 Selection header (same as Notes)
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

            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    GlassCard(
                        modifier = Modifier
                            .padding(24.dp)
                            .clickable {
                                navController.navigate(
                                    Routes.EditTodo.create(-1)
                                )
                            }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Create your first todo",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Tap to add a task",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            } else {

                LazyColumn {
                    items(todos, key = { it.id }) { todo ->
                        val isSelected = selectedIds.contains(todo.id)

                        SwipeToDismiss(
                            onSwipedLeft = {
                                viewModel.toggleCompleted(todo)
                            },
                            onSwipedRight = {
                                viewModel.toggleCompleted(todo)
                            }
                        ) {
                            GlassCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 80.dp)
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
                                selected = isSelected || todo.completed
                            ) {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = todo.completed,
                                            onCheckedChange = {
                                                viewModel.toggleCompleted(todo)
                                            }
                                        )

                                        Spacer(Modifier.width(8.dp))

                                        Text(
                                            text = todo.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color =
                                                if (todo.completed)
                                                    Color.White.copy(alpha = 0.4f)
                                                else Color.White
                                        )
                                    }

                                    // 🔔 Due date / reminder row
                                    if (todo.dueDate != null || todo.reminderTime != null) {
                                        Spacer(Modifier.height(4.dp))

                                        Row(
                                            modifier = Modifier.padding(start = 40.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            todo.dueDate?.let {
                                                Text(
                                                    text = "📅 ${formatDateTime(it)}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.White.copy(alpha = 0.6f)
                                                )
                                            }

                                            todo.reminderTime?.let {
                                                Text(
                                                    text = "⏰ ${formatDateTime(it)}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.White.copy(alpha = 0.6f)
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

        // 🗑 DELETE CONFIRMATION DIALOG
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
}
