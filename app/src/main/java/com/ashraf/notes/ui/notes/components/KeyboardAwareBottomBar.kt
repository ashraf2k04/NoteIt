package com.ashraf.notes.ui.notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun KeyboardAwareBottomBar(
    modifier : Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(
                bottom = WindowInsets.ime
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .fillMaxWidth()
    ) {
        content()
    }
}
