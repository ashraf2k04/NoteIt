package com.ashraf.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GlassyBackground(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    val colors =
        if (isDark) {
            listOf(
                Color(0xEE1C1C1E),
                Color(0xFF000000)
            )
        } else {
            listOf(
                Color(0xEFFFFFFF),
                Color(0xFFE0E0E0)
            )
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = colors,
                    radius = 900f
                )
            )
    ) {
        content()
    }
}
