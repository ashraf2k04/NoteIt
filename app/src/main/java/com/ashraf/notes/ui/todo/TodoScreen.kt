package com.ashraf.notes.ui.todo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashraf.notes.ui.components.TodoItem

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todos by viewModel.todos.collectAsState()

    LazyColumn {
        items(todos) {
            TodoItem(
                title = it.title,
                checked = it.completed,
                onToggle = { viewModel.toggle(it) }
            )
        }
    }
}
