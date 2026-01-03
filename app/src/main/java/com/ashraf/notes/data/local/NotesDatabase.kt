package com.ashraf.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashraf.notes.data.local.note.NoteDao
import com.ashraf.notes.data.local.note.NoteEntity
import com.ashraf.notes.data.local.todo.TodoDao
import com.ashraf.notes.data.local.todo.TodoEntity

@Database(
    entities = [NoteEntity::class, TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun todoDao(): TodoDao
}
