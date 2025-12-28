package com.ashraf.notes.ui.notes.helpers


fun applyHighlight(
    start: Int,
    end: Int,
    color: HighlightColor,
    highlights: List<HighlightRange>
): List<HighlightRange> {
    if (start >= end) return highlights

    val newRange = HighlightRange(start, end, color)

    return highlights
        .filterNot { it.start >= start && it.end <= end }
        .plus(newRange)
}