package com.ashraf.notes.widget

import android.content.Context
import androidx.room.Room
import com.ashraf.notes.data.local.NotesDatabase

object WidgetDatabaseProvider {

    @Volatile
    private var INSTANCE: NotesDatabase? = null

    fun getDatabase(context: Context): NotesDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                "notes_db"
            ).build().also { INSTANCE = it }
        }
    }
}
