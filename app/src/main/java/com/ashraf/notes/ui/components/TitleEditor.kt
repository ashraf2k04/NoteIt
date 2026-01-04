package com.ashraf.notes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TitleEditor(
    title: String,
    onTitleChange: (String) -> Unit,
    onSave: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = title,
        onValueChange = onTitleChange,
        placeholder = { Text("Title") },
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    // 🔥 Focus LOST → autosave
                    onSave()
                }
                isFocused = focusState.isFocused
            },
        trailingIcon = {
            if (isFocused) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save title",
                    modifier = Modifier.clickable { onSave() }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit title",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}