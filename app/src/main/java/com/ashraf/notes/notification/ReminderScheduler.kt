package com.ashraf.notes.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class ReminderScheduler(
    private val context: Context
) {

    fun schedule(id: Long, title: String, time: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)
            .putExtra("title", title)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }
}
