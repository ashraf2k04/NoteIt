package com.ashraf.notes.data.local.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    // FULL NOTE TEXT
    val text: String,

    // JSON of HighlightSpan[]
    val highlightsJson: String,

    val createdAt: Long = System.currentTimeMillis()
)

