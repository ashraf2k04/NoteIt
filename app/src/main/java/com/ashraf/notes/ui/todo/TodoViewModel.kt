package com.ashraf.notes.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashraf.notes.data.local.todo.TodoDao
import com.ashraf.notes.data.local.todo.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val dao: TodoDao
) : ViewModel() {

    val todos = dao.getTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun add(title: String) {
        viewModelScope.launch {
            dao.insert(TodoEntity(title = title))
        }
    }

    fun toggle(todo: TodoEntity) {
        viewModelScope.launch {
            dao.update(todo.copy(completed = !todo.completed))
        }
    }
}
