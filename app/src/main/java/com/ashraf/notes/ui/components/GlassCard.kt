package com.ashraf.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()

    val background =
        if (selected) {
            scheme.primary.copy(alpha = 0.25f)
        } else {
            if (isDark)
                Color.White.copy(alpha = 0.12f)
            else
                Color.Black.copy(alpha = 0.05f)
        }

    val border =
        if (selected) scheme.primary
        else scheme.onSurface.copy(alpha = 0.12f)

    Box(
        modifier = modifier
            .background(
                background,
                RoundedCornerShape(24.dp)
            )
            .border(
                1.dp,
                border,
                RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}
