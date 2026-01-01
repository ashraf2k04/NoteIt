package com.ashraf.notes.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/* ---------- CORE BLUES ---------- */

val BlueNavyDark   = Color(0xFF0B1E34) // Deep dark background
val BlueNavy       = Color(0xFF102A44)
val BluePrimary    = Color(0xFF1E63D5) // Buttons, FAB
val BlueAccent     = Color(0xFF3B82F6)
val BlueSoft       = Color(0xFF8FB6FF)

/* ---------- LIGHT THEME TOKENS ---------- */

object LightNotesColors {

    // Backgrounds
    val background      = Color(0xFFEAF2FF) // bluish white
    val surface         = Color(0xFFF6F9FF)
    val card            = Color(0xFFFFFFFF)

    // Glass
    val glass            = Color(0xCCFFFFFF)
    val glassBorder      = Color(0x330B5ED7)

    // Primary
    val primary          = BluePrimary
    val onPrimary        = Color.White

    // Text
    val textPrimary      = Color(0xFF0F172A)
    val textSecondary    = Color(0xFF475569)
    val textHint         = Color(0xFF94A3B8)

    // Icons
    val iconPrimary      = BluePrimary
    val iconSecondary    = Color(0xFF64748B)

    // UI
    val divider          = Color(0xFFD6E4FF)
    val border           = Color(0xFFCBDDFF)
}

/* ---------- DARK THEME TOKENS ---------- */

object DarkNotesColors {

    // Backgrounds
    val background      = Color(0xFF08192C)
    val surface         = Color(0xFF0E223A)
    val card            = Color(0xFF142C44)

    // Glass
    val glass            = Color(0x66142C44)
    val glassBorder      = Color(0x332A6FFF)

    // Primary
    val primary          = BlueAccent
    val onPrimary        = Color(0xFF08192C)

    // Text
    val textPrimary      = Color(0xFFF8FAFC)
    val textSecondary    = Color(0xFFCBD5E1)
    val textHint         = Color(0xFF94A3B8)

    // Icons
    val iconPrimary      = Color(0xFFBFDBFE)
    val iconSecondary    = Color(0xFF93C5FD)

    // UI
    val divider          = Color(0xFF1E3A5F)
    val border           = Color(0xFF274C77)
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
