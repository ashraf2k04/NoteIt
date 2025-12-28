package com.ashraf.notes.di

import android.content.Context
import androidx.room.Room
import com.ashraf.notes.data.local.NotesDatabase
import com.ashraf.notes.data.local.note.NoteDao
import com.ashraf.notes.data.local.todo.TodoDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_db"
        ).build()

    @Provides
    fun provideNoteDao(db: NotesDatabase): NoteDao = db.noteDao()

    @Provides
    fun provideTodoDao(db: NotesDatabase): TodoDao = db.todoDao()
}
