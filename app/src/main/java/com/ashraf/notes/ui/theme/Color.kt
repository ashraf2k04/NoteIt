package com.ashraf.notes.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/* ---------- CORE BLUES ---------- */

val BluePrimary = Color(0xFF1E63D5)
val BlueAccent  = Color(0xFF1C52F5)
val BlueNavyDark = Color(0xFF0B1E34)

// Deep dark background
val BlueNavy = Color(0xF01F4079)

val BlueSoft = Color(0xFF7DABFF)

/* ---------- LIGHT THEME ---------- */

object LightNotesColors {

    // Backgrounds

    val background = Color(0xFF9AC0FD)
    val surface = Color(0xFFF6F9FF)

    val card = Color(0xFFFFFFFF) // Glass
    val glass = Color(0xCCFFFFFF)
    val glassBorder = Color(0x330B5ED7)

    // Containers
    val surfaceContainer = Color(0xFF88A0D5)
    val surfaceContainerHigh = Color(0xFFA0BAFD)

    // Primary
    val primary = BluePrimary
    val onPrimary = BlueNavyDark

    val primaryContainer = BlueSoft
    val onPrimaryContainer = Color(0xFFA6C0E8)

    // Navigation indicator
    val secondaryContainer = Color(0xFFA1BEF1)

    // Text
    val onSurface = Color(0xFF0F172A)
    val onSurfaceVariant = Color(0xFF475569)

    // UI
    val border = Color(0xFFCBDDFF)
    val divider = Color(0xFFD6E4FF)
    val textPrimary = Color(0xFF0F172A)
    val textSecondary = Color(0xFF475569)
    val textHint = Color(0xFF94A3B8)
    val iconPrimary = BluePrimary
    val iconSecondary = Color(0xFF64748B)
}

/* ---------- DARK THEME ---------- */

object DarkNotesColors {

    // Backgrounds
    val background = Color(0xFF080E2C)
    val surface = Color(0xFF0E223A)

    // Containers
    val surfaceContainer = Color(0xFF0F2942)
    val surfaceContainerHigh = Color(0xFF142C44)

    // Primary
    val primary = BlueAccent
    val onPrimary = Color(0xFF08192C)

    val glass = Color(0x66142C44)
    val glassBorder = Color(0x332A6FFF)

    val iconPrimary = Color(0xFFBFDBFE)
    val iconSecondary = Color(0xFF93C5FD)

    val textPrimary = Color(0xFFF8FAFC)
    val textSecondary = Color(0xFFCBD5E1)
    val textHint = Color(0xFF94A3B8)

    val primaryContainer = BlueSoft
    val onPrimaryContainer = Color(0xFFA6C0E8)

    // Navigation indicator
    val secondaryContainer = Color(0xFF1F3B63)

    // Text
    val onSurface = Color(0xFFF8FAFC)
    val onSurfaceVariant = Color(0xFFCBD5E1)

    // UI
    val border = Color(0xFF274C77)
    val divider = Color(0xFF1E3A5F)
    val card = Color(0xFF142C44)
}


/* ---------- GRADIENTS ---------- */

val LightBgGradient = Brush.verticalGradient(
    listOf(
        LightNotesColors.background,
        LightNotesColors.surface
    )
)

val DarkBgGradient = Brush.verticalGradient(
    listOf(
        DarkNotesColors.background,
        BlueNavy
    )
)

/* ---------- GLASS CARDS ---------- */

val LightGlassCard = Color(0xEEFFFFFF)
val DarkGlassCard  = Color(0xCC142C44)
