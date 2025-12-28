package com.ashraf.notes.ui.notes.helpers

import kotlinx.serialization.Serializable

@Serializable
data class NoteString(
    var title : String = "",
    var text: String = "",
    var bold: Boolean = false,
    var italic: Boolean = false,
    var underline: Boolean = false,
    var fontSizeSp: Float = 20f,
    val textColor: Long = 0xFFFFFFFF,
    var highlightColor: List<HighlightRange> = emptyList(),

    var selection: HighlightRange = HighlightRange(0, 0, HighlightColor.YELLOW)
)