package com.ashraf.notes.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.notes.components.EditorTopRow
import com.ashraf.notes.ui.notes.components.KeyboardAwareBottomBar
import com.ashraf.notes.ui.notes.components.TitleEditor
import com.ashraf.notes.ui.todo.components.showDateTimePicker
import com.ashraf.notes.ui.todo.helpers.formatDateTime
import androidx.activity.compose.BackHandler
import androidx.compose.material3.HorizontalDivider
import com.ashraf.notes.ui.todo.components.CircularCheckbox


@Composable
fun EditTodoScreen(
    todoId: Long,
    navController: androidx.navigation.NavController,
    viewModel: TodoViewModel = hiltViewModel(),
    initialTitle: String
) {

    var hasChanges by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf(initialTitle) }
    var completed by remember { mutableStateOf(false) }
    var dueDate by remember { mutableStateOf<Long?>(null) }
    var reminder by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(todoId) {
        if (todoId != -1L) {
            viewModel.loadTodo(todoId)?.let { todo ->
                title = todo.title
                completed = todo.completed
                dueDate = todo.dueDate
                reminder = todo.reminderTime
                hasChanges = false
            }
        }
    }

    BackHandler {
        if (hasChanges) {
            showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {

        EditorTopRow(
            onBack = {
                if (hasChanges) {
                    showExitDialog = true
                } else {
                    navController.popBackStack()
                }
            },
            onSave = {
                if (todoId == -1L) {
                    viewModel.insertTodo( title, completed, dueDate, reminder)
                } else {
                    viewModel.updateTodo(todoId, title, completed, dueDate, reminder)
                }
                navController.popBackStack()
            }
        )

        TitleEditor(
            title = title,
            onTitleChange = {
                title = it
                hasChanges = true
            },
            onSave = {}
        )

        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            CircularCheckbox(
                checked = completed,
                onCheckedChange = {
                    completed = it
                    hasChanges = true
                }
            )
            Spacer(Modifier.width(8.dp))
            Text("Mark as completed")
        }

        Spacer(Modifier.weight(1f)) // 🔥 pushes buttons to bottom

        KeyboardAwareBottomBar(Modifier) {
            Column {
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                val context = LocalContext.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            showDateTimePicker(context) { selected ->
                                dueDate = selected
                                hasChanges = true
                            }
                        }
                    ) {
                        Text(
                            if (dueDate == null) "Set Due Date"
                            else "Due: ${formatDateTime(dueDate!!)}"
                        )
                    }

                    Button(
                        onClick = {
                            showDateTimePicker(context) { selected ->
                                reminder = selected
                                hasChanges = true
                            }
                        }
                    ) {
                        Text(
                            if (reminder == null) "Set Reminder"
                            else "Remind: ${formatDateTime(reminder!!)}"
                        )
                    }
                }
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
                        if (todoId == -1L) {
                            viewModel.insertTodo( title, completed, dueDate, reminder)
                        } else {
                            viewModel.updateTodo(todoId, title, completed, dueDate, reminder)
                        }
                        showExitDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = {
                            showExitDialog = false
                            navController.popBackStack()
                        }
                    ) {
                        Text("Discard")
                    }

                    TextButton(
                        onClick = { showExitDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}