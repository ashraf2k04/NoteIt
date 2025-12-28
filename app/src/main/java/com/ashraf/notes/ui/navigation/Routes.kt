package com.ashraf.notes.ui.navigation

import android.net.Uri

sealed class Routes(val route: String) {

    data object Notes : Routes("notes")
    data object Todos : Routes("todos")

    object EditNote : Routes(
        "edit_note/{noteId}?title={title}"
    ) {
        fun create(noteId: Long, title: String = "") =
            "edit_note/$noteId?title=${Uri.encode(title)}"
    }


    data object EditTodo : Routes("edit_todo/{todoId}") {
        fun create(todoId: Long) = "edit_todo/$todoId"
    }
}
