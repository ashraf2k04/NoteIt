package com.ashraf.notes.ui.todo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(todoId: Long) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Todo") })
        }
    ) { padding ->
        TextField(
            modifier = androidx.compose.ui.Modifier.padding(padding),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Todo title") }
        )
    }
}

