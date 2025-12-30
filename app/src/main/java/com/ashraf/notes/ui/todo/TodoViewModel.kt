package com.ashraf.notes.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashraf.notes.data.local.todo.TodoDao
import com.ashraf.notes.data.local.todo.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao
) : ViewModel() {

    val todos = todoDao.getTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun insertTodo(
        title: String,
        completed: Boolean,
        dueDate: Long?,
        reminderTime: Long?,
        onResult: (Long) -> Unit = {}
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val id = todoDao.insert(
                TodoEntity(
                    title = title,
                    completed = completed,
                    dueDate = dueDate,
                    reminderTime = reminderTime
                )
            )
            onResult(id)
        }
    }

    suspend fun loadTodo(todoId: Long): TodoEntity? {
        return todoDao.getTodoById(todoId)
    }


    fun updateTodo(
        id: Long,
        title: String,
        completed: Boolean,
        dueDate: Long?,
        reminderTime: Long?
    ) {
        if (id == -1L || title.isBlank()) return

        viewModelScope.launch {
            todoDao.update(
                TodoEntity(
                    id = id,
                    title = title,
                    completed = completed,
                    dueDate = dueDate,
                    reminderTime = reminderTime
                )
            )
        }
    }

    fun toggleCompleted(todo: TodoEntity) {
        viewModelScope.launch {
            todoDao.update(todo.copy(completed = !todo.completed))
        }
    }

    fun deleteTodos(ids: List<Long>) {
        viewModelScope.launch {
            todoDao.deleteTodos(ids)
        }
    }
}

