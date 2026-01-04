package com.ashraf.notes.ui.notes.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashraf.notes.ui.notes.helpers.NoteString
import com.ashraf.notes.ui.notes.helpers.toComposeColor

@Composable
fun HighlightableTextField(
    value: TextFieldValue,
    note: NoteString,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier
) {
    val annotated = remember(value.text, note.highlightColor) {
        buildAnnotatedString {
            append(value.text)
            note.highlightColor.forEach {
                if (it.end <= value.text.length) {
                    addStyle(
                        SpanStyle(background = it.color.toComposeColor()),
                        it.start,
                        it.end
                    )
                }
            }
        }
    }

    TextField(
        value = value.copy(annotatedString = annotated),
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        textStyle = TextStyle(
            fontSize = note.fontSizeSp.sp,
            fontWeight = if (note.bold) FontWeight.Bold else FontWeight.Normal,
            fontStyle = if (note.italic) FontStyle.Italic else FontStyle.Normal,
            textDecoration = if (note.underline) TextDecoration.Underline else TextDecoration.None,
            color = Color(note.textColor)
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}