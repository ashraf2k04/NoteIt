package com.ashraf.notes.ui.components

import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AddItemDialog(
    title: String,
    hint: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column {

                // TITLE
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(16.dp))

                // INPUT
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text(
                            hint,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.DarkGray,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White
                    )
                )

                Spacer(Modifier.height(24.dp))

                // ACTIONS
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.White.copy(alpha = 0.7f))
                    }

                    Spacer(Modifier.width(8.dp))

                    TextButton(
                        enabled = text.isNotBlank(),
                        onClick = { onConfirm(text) }
                    ) {
                        Text("Create", color = Color.White)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Alert(){
    AddItemDialog(
        title = " Create New Note",
        hint = "Note title",
        onDismiss = { },
        onConfirm = { }
    )
}
