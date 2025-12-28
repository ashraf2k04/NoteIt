package com.ashraf.notes.ui.notes.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.TextIncrease
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ashraf.notes.ui.notes.helpers.HighlightColor
import com.ashraf.notes.ui.notes.helpers.toComposeColor


@Composable
fun NoteEditorToolbar(
    onHighlight: (HighlightColor) -> Unit,
    onBold: () -> Unit,
    onItalic: () -> Unit,
    onUnderline: () -> Unit,
    onIncreaseFont: () -> Unit,   // 👈 NEW
    onDecreaseFont: () -> Unit    // 👈 NEW
) {
    BottomAppBar( modifier = Modifier.fillMaxWidth() )
    {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ){
            item {
                IconButton(
                    onClick = onDecreaseFont
                ) {
                    Icon(Icons.Default.TextDecrease, contentDescription = "Decrease font")
                }
            }

            item {
                IconButton(
                    onClick = onIncreaseFont
                ) {
                    Icon(Icons.Default.TextIncrease, contentDescription = "Increase font")
                }
            }

            item {
                IconButton(
                    onClick = onBold,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.FormatBold, null)
                }
            }

            item {
                IconButton(
                    onClick = onItalic,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.FormatItalic, null)
                }
            }

            item {
                IconButton(
                    onClick = onUnderline,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.FormatUnderlined, null)
                }
            }

            items(HighlightColor.entries) { color ->
                IconButton(
                    onClick = { onHighlight(color) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .background(
                                color.toComposeColor(),
                                shape = CircleShape
                            )
                    )
                }
            }
        }

    }
}


