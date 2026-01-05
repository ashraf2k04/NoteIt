package com.ashraf.notes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

/* ---------- LIGHT SCHEME ---------- */

private val LightScheme = lightColorScheme(

    /* Primary */
    primary = LightNotesColors.primary,
    onPrimary = LightNotesColors.onPrimary,

    /* Secondary (used by NavigationBar unselected state internally) */
    secondary = LightNotesColors.iconSecondary,
    onSecondary = LightNotesColors.textPrimary,

    /* Backgrounds */
    background = LightNotesColors.background,
    onBackground = LightNotesColors.textPrimary,

    /* Surfaces */
    surface = LightNotesColors.surface,
    onSurface = LightNotesColors.onSurface,

    /* Cards / Containers */
    surfaceVariant = LightNotesColors.card,
    onSurfaceVariant = LightNotesColors.textSecondary,

    /* Borders / Dividers */
    outline = LightNotesColors.border,
    outlineVariant = LightNotesColors.divider,

    primaryContainer = LightNotesColors.primaryContainer,
    onPrimaryContainer = LightNotesColors.onPrimaryContainer,

    surfaceContainerHigh = LightNotesColors.surfaceContainerHigh,

    surfaceContainer = LightNotesColors.surfaceContainer,
    secondaryContainer = LightNotesColors.secondaryContainer,
)

/* ---------- DARK SCHEME ---------- */

private val DarkScheme = darkColorScheme(

    /* Primary */
    primary = DarkNotesColors.primary,
    onPrimary = DarkNotesColors.onPrimary,

    /* Secondary */
    secondary = DarkNotesColors.iconSecondary,
    onSecondary = DarkNotesColors.textPrimary,

    /* Backgrounds */
    background = DarkNotesColors.background,
    onBackground = DarkNotesColors.textPrimary,

    /* Surfaces */
    surface = DarkNotesColors.surface,
    onSurface = DarkNotesColors.textPrimary,

    /* Cards / Containers */
    surfaceVariant = DarkNotesColors.card,

    surfaceContainer = DarkNotesColors.surfaceContainer,
    onSurfaceVariant = DarkNotesColors.onSurfaceVariant,

    /* Borders / Dividers */
    outline = DarkNotesColors.border,
    outlineVariant = DarkNotesColors.divider,

    primaryContainer = DarkNotesColors.primaryContainer,
    onPrimaryContainer = DarkNotesColors.onPrimaryContainer,

    surfaceContainerHigh = DarkNotesColors.surfaceContainerHigh,

    secondaryContainer = DarkNotesColors.secondaryContainer,
)

/* ---------- THEME ---------- */

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkScheme else LightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
