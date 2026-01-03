package com.ashraf.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashraf.notes.ui.theme.DarkGlassCard
import com.ashraf.notes.ui.theme.LightGlassCard

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()

    val background =
        if (selected) {
            scheme.primary.copy(alpha = 0.22f)
        } else {
            if (isDark) DarkGlassCard else LightGlassCard
        }

    val border =
        if (selected) {
            scheme.primary
        } else {
            scheme.onSurface.copy(alpha = 0.10f)
        }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = background,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = border,
                shape = RoundedCornerShape(8.dp)
            )
            .padding( 8.dp)
    ) {
        content()
    }
}


@Composable
fun GlassCard2(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()

    val baseGlassColor = if (isDark) {
        Color(0xFF0B1A33)
    } else {
        Color.White
    }

    val gradient = Brush.verticalGradient(
        colors = if (isDark) {
            listOf(
                baseGlassColor.copy(alpha = 0.35f),
                baseGlassColor.copy(alpha = 0.18f)
            )
        } else {
            listOf(
                baseGlassColor.copy(alpha = 0.75f),
                baseGlassColor.copy(alpha = 0.55f)
            )
        }
    )

    val borderColor = if (selected) {
        scheme.primary.copy(alpha = 0.9f)
    } else {
        Color.White.copy(alpha = if (isDark) 0.15f else 0.35f)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            // Glass blur layer
            .blur(20.dp)
            // Gradient glass surface
            .background(gradient)
            // Border highlight
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GC(){
    GlassCard2(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .height(32.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "note.title",
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "...",
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
