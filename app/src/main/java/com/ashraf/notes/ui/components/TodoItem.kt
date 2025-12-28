package com.ashraf.notes.ui.components

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TodoItem(
    title: String,
    checked: Boolean,
    onToggle: () -> Unit
) {
    GlassCard {
        Checkbox(checked = checked, onCheckedChange = { onToggle() })
        Text(text = title, color = Color.White)
    }
}
