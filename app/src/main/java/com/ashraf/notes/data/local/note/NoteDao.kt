package com.ashraf.notes.data.local.note

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity) : Long

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Long): NoteEntity?

    @Query("DELETE FROM notes WHERE id IN (:ids)")
    suspend fun deleteNotes(ids: List<Long>)

    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    suspend fun getNotesOnce(): List<NoteEntity>


}
