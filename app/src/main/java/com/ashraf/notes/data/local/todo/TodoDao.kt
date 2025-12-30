package com.ashraf.notes.data.local.todo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos ORDER BY completed ASC, dueDate ASC")
    fun getTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoEntity): Long

    @Update
    suspend fun update(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id IN (:ids)")
    suspend fun deleteTodos(ids: List<Long>)

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun getTodoById(id: Long): TodoEntity?

}
