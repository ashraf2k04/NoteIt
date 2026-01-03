package com.ashraf.notes.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashraf.notes.data.local.todo.TodoDao
import com.ashraf.notes.data.local.todo.TodoEntity
import com.ashraf.notes.notification.ReminderScheduler
import com.ashraf.notes.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao,
    private val reminderScheduler : ReminderScheduler
) : ViewModel() {

    val todosState: StateFlow<UiState<List<TodoEntity>>> =
        todoDao.getTodos()
            .map<List<TodoEntity>, UiState<List<TodoEntity>>> {
                UiState.Success(it)
            }
            .catch { emit(UiState.Error("Failed to load todos")) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                UiState.Loading
            )

    fun insertTodo(
        title: String,
        completed: Boolean,
        dueDate: Long?,
        reminderTime: Long?
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

            if (reminderTime != null) {
                reminderScheduler.schedule(id, title, reminderTime)
            }
        }
    }


    suspend fun loadTodo(todoId: Long): TodoEntity? =
        todoDao.getTodoById(todoId)

    fun updateTodo(
        id: Long,
        title: String,
        completed: Boolean,
        dueDate: Long?,
        reminderTime: Long?
    ) {
        if (id == -1L || title.isBlank()) return
        viewModelScope.launch {

            reminderScheduler.cancel(id)

            todoDao.update(
                TodoEntity(
                    id = id,
                    title = title,
                    completed = completed,
                    dueDate = dueDate,
                    reminderTime = reminderTime
                )
            )
            if (reminderTime != null) {
                reminderScheduler.schedule(id, title, reminderTime)
            }
        }
    }

    fun toggleCompleted(todo: TodoEntity) {
        viewModelScope.launch {
            todoDao.update(todo.copy(completed = !todo.completed))
        }
    }

    fun deleteTodos(ids: List<Long>) {
        viewModelScope.launch {
            ids.forEach { reminderScheduler.cancel(it) }
            todoDao.deleteTodos(ids)
        }
    }
}
