package com.ashraf.notes.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.ashraf.notes.R

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // 🔴 Android 13+ runtime permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionGranted =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (!permissionGranted) {
                // Permission denied → do nothing safely
                return
            }
        }

        val title = intent.getStringExtra("title") ?: "Todo Reminder"

        val notification = NotificationCompat.Builder(context, "todo_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reminder")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context)
            .notify(System.currentTimeMillis().toInt(), notification)
    }
}
