package com.ashraf.notes.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent

class TodoWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        val notes = WidgetDatabaseProvider
            .getDatabase(context)
            .noteDao()
            .getNotesOnce()

        provideContent {
            TodoWidgetContent(notes)
        }
    }
}
