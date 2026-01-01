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
import androidx.compose.ui.unit.dp
import com.ashraf.notes.ui.theme.DarkGlassCard
import com.ashraf.notes.ui.theme.LightGlassCard

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
            scheme.primary.copy(alpha = 0.22f)
        } else {
            if (isDark) DarkGlassCard else LightGlassCard
        }

    val border =
        if (selected) {
            scheme.primary
        } else {
            scheme.onSurface.copy(alpha = 0.10f)
        }

    Box(
        modifier = modifier
            .background(
                color = background,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = border,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}
