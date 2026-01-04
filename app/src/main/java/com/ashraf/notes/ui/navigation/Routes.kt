package com.ashraf.notes.ui.navigation


sealed class Routes(val route: String) {

    data object Notes : Routes("notes")
    data object Todos : Routes("todos")

    object EditNote : Routes(
        "edit_note/{noteId}"
    ) {
        fun create(noteId: Long) =
            "edit_note/$noteId"
    }


    data object EditTodo : Routes(
        "edit_todo/{todoId}"
    ) {
        fun create(todoId: Long) =
            "edit_todo/$todoId"
    }
}
