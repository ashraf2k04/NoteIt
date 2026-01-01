package com.ashraf.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ashraf.notes.ui.theme.DarkBgGradient
import com.ashraf.notes.ui.theme.LightBgGradient

@Composable
fun GlassyBackground(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDark) DarkBgGradient else LightBgGradient
            )
    ) {
        content()
    }
}
