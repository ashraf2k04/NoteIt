package com.ashraf.notes.ui.notes.helpers

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class HighlightRange(
    val start: Int,
    val end: Int,
    val color: HighlightColor
)

@Serializable
enum class HighlightColor {
    YELLOW, GREEN, BLUE, PINK, ORANGE
}

fun HighlightColor.toComposeColor(): Color = when (this) {
    HighlightColor.YELLOW -> Color(0xFFFFF59D)
    HighlightColor.GREEN  -> Color(0xFFA5D6A7)
    HighlightColor.BLUE   -> Color(0xFF90CAF9)
    HighlightColor.PINK   -> Color(0xFFF48FB1)
    HighlightColor.ORANGE -> Color(0xFFFFCC80)
}

