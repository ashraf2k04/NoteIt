package com.ashraf.notes.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/* ---------- GLASSY DARK ---------- */

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xC82D689B),
    secondary = Color(0xFFFFD54F),
    tertiary = Color(0xFF80CBC4),

    background = Color(0xFF5E799A),
    surface = Color(0xFF000000), // translucent surface
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

/* ---------- OPTIONAL LIGHT ---------- */

private val LightColorScheme = lightColorScheme(
    primary = Color(0xCB1C539A),
    secondary = Color(0xFFFFB300),
    tertiary = Color(0xFF00796B),

    background = Color(0xFFF4F6FA),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = true, // 🔥 force dark by default (glassy UI)
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
