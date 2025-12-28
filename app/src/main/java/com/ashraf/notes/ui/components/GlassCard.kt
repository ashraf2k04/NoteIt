package com.ashraf.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val backgroundBrush =
        if (selected) {
            Brush.linearGradient(
                listOf(
                    Color(0xFF4FC3F7).copy(alpha = 0.25f),
                    Color(0xFF4FC3F7).copy(alpha = 0.15f)
                )
            )
        } else {
            Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = 0.12f),
                    Color.White.copy(alpha = 0.05f)
                )
            )
        }

    val borderColor =
        if (selected) Color(0xFF4FC3F7)
        else Color.White.copy(alpha = 0.15f)

    Box(
        modifier = modifier
            .background(
                brush = backgroundBrush,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}
