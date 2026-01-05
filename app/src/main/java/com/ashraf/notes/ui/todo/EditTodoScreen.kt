package com.ashraf.notes.ui.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.components.EditorTopRow
import com.ashraf.notes.ui.components.TitleEditor
import com.ashraf.notes.ui.components.DeleteAlert
import com.ashraf.notes.ui.todo.helpers.formatDateTime
import com.ashraf.notes.ui.todo.components.CheckMarker
import com.ashraf.notes.ui.todo.components.BottomActionBar
import com.ashraf.notes.ui.todo.components.showDateTimePicker


@Composable
fun EditTodoScreen(
    todoId: Long,
    navController: androidx.navigation.NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {

    var hasChanges by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
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
        if (hasChanges) showExitDialog = true
        else navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.union(WindowInsets.systemBars).asPaddingValues())
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
                    viewModel.insertTodo(title, completed, dueDate, reminder)
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

        CheckMarker(
            completed = completed,
            onCheckedChange = {
                completed = it
                hasChanges = true
            }
        )

        Spacer(Modifier.weight(1f))

        dueDate?.let {
            Text(
                text = "Due by : ${formatDateTime(it)}",
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        reminder?.let {
            Text(
                text = "Reminder by : ${formatDateTime(it)}",
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        val context = LocalContext.current

        BottomActionBar(
            dueDate = dueDate,
            reminder = reminder,
            onSetDueDate = {
                showDateTimePicker(context) { selected ->
                    dueDate = selected
                    hasChanges = true
                }
            },
            onSetReminder = {
                showDateTimePicker(context) { selected ->
                    reminder = selected
                    hasChanges = true
                }
            }
        )
    }

    if (showExitDialog) {
        DeleteAlert(
            onDismiss = {
                showExitDialog = false
            },
            onSave = {
                if (todoId == -1L) {
                    viewModel.insertTodo(title, completed, dueDate, reminder)
                } else {
                    viewModel.updateTodo(todoId, title, completed, dueDate, reminder)
                }
                showExitDialog = false
                navController.popBackStack()
            },
            onDiscard = {
                showExitDialog = false
                navController.popBackStack()
            }
        )
    }
}