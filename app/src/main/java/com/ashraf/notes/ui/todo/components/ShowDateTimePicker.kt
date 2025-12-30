package com.ashraf.notes.ui.todo.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar


fun showDateTimePicker(
    context : Context,
    onDateTimeSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            TimePickerDialog(
                context,
                { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    onDateTimeSelected(calendar.timeInMillis)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ).show()

        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
