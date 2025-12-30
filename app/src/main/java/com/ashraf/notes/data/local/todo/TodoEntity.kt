package com.ashraf.notes.data.local.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val completed: Boolean = false,
    val dueDate: Long? = null,
    val reminderTime: Long? = null
)
