package com.ashraf.notes.ui.todo.helpers

import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(time: Long): String {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return sdf.format(Date(time))
}
