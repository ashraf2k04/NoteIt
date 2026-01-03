package com.ashraf.notes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.ashraf.notes.ui.components.GlassyBackground
import com.ashraf.notes.ui.home.HomeScreen
import com.ashraf.notes.ui.theme.NotesTheme
import com.ashraf.notes.widget.RouteKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔔 Notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setContent {
            NotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 🔔 Read todoId from notification
                    val todoIdFromNotification =
                        intent?.getLongExtra("todo_id", -1L)

                    // 🔁 Navigate when notification is clicked
                    LaunchedEffect(todoIdFromNotification) {
                        if (todoIdFromNotification != null && todoIdFromNotification != -1L) {
                            navController.navigate("todo_edit/$todoIdFromNotification") {
                                launchSingleTop = true
                            }
                            intent?.removeExtra("todo_id") // 🔥 prevent re-trigger
                        }
                    }

                    // Existing Glance deep link handling (keep this)
                    val route = intent?.extras?.getString(RouteKey.name)
                    LaunchedEffect(route) {
                        route?.let {
                            navController.navigate(it) {
                                launchSingleTop = true
                            }
                            intent?.replaceExtras(Bundle())
                        }
                    }

                    GlassyBackground {
                        HomeScreen(navController)
                    }
                }
            }
        }

    }
}

