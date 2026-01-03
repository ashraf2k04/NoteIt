package com.ashraf.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SwipeToDismiss(
    modifier: Modifier = Modifier,
    onSwipedLeft: () -> Unit = {},
    onSwipedRight: () -> Unit = {},
    swipeThreshold: Float = 180f,
    isSelected: Boolean,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var swiped by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when {
                            offsetX > swipeThreshold -> {
                                onSwipedRight()
                                swiped = true
                            }
                            offsetX < -swipeThreshold -> {
                                onSwipedLeft()
                                swiped = true
                            }
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
    ) {

        // Background actions
        AnimatedVisibility(
            visible = abs(offsetX) > swipeThreshold / 2,
            modifier = Modifier.fillMaxSize().align(Alignment.CenterStart)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color =
                            if (offsetX > 0)
                                Color(0xE979C27E) // green
                            else
                                Color(0xC9C94747), // red
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp),
                contentAlignment =
                    if (offsetX > 0)
                        Alignment.CenterStart
                    else
                        Alignment.CenterEnd
            ) {
                Icon(
                    imageVector =
                        if (offsetX > 0)
                             Icons.Default.CheckCircle
                        else{
                            if (isSelected)Icons.Default.DeleteSweep else Icons.Default.DeleteForever},
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        // Foreground content
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
        ) {
            content()
        }
    }
}
