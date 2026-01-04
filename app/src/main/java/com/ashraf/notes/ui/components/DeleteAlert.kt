package com.ashraf.notes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteAlert(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onDiscard: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Unsaved changes") },
        text = { Text("Do you want to save your changes before leaving?") },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                TextButton(
                    onClick = onDiscard
                ) { Text("Discard") }

                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}
