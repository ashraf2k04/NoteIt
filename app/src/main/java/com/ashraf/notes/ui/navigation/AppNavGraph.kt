package com.ashraf.notes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ashraf.notes.ui.notes.EditNoteScreen
import com.ashraf.notes.ui.todo.EditTodoScreen
import com.ashraf.notes.ui.notes.NotesScreen
import com.ashraf.notes.ui.todo.TodoScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Notes.route
    ) {

        composable(Routes.Notes.route) {
            NotesScreen(navController)
        }

        composable(Routes.Todos.route) {
            TodoScreen(navController)
        }

        composable(
            route =  "edit_note/{noteId}?title={title}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("noteId")
                ?.toLong() ?: -1

            val title = backStackEntry.arguments
                ?.getString("title") ?: ""

            EditNoteScreen(
                noteId = id,
                navController = navController,
                initialTitle = title
            )
        }

        composable(
    route = "edit_todo/{todoId}?title={title}"
) { backStackEntry ->

    val id = backStackEntry.arguments
        ?.getString("todoId")
        ?.toLong() ?: -1

    val title = backStackEntry.arguments
        ?.getString("title") ?: ""

    EditTodoScreen(
        todoId = id,
        navController = navController,
        initialTitle = title
    )
}

    }
}
