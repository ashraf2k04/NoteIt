package com.ashraf.notes.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.ashraf.notes.MainActivity
import com.ashraf.notes.data.local.note.NoteEntity
import com.ashraf.notes.ui.navigation.Routes


@Composable
fun TodoWidgetContent(
    notes: List<NoteEntity>
) {
    Column(
        modifier = GlanceModifier.padding(12.dp)
    ) {
        if (notes.isEmpty()) {
            Text("No notes yet")
        } else {
            notes.forEach { note ->
                Text(
                    text = note.title,
                    modifier = GlanceModifier
                        .padding(vertical = 4.dp)
                        .clickable(
                            actionStartActivity<MainActivity>(
                                parameters = actionParametersOf(
                                    RouteKey to Routes.EditNote.create(note.id)
                                )
                            )
                        )
                )
            }
        }
    }
}



