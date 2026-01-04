package com.ashraf.notes.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashraf.notes.ui.navigation.Routes

@Composable
fun BottomNavigationBar(
    scaffoldRoute : String?,
    navController : NavController
)
{
    if (
        scaffoldRoute == Routes.Notes.route ||
        scaffoldRoute == Routes.Todos.route
    ) {
        NavigationBar(
            tonalElevation = 4.dp
        ) {
            NavigationBarItem(
                selected = scaffoldRoute == Routes.Notes.route,
                onClick = {
                    navController.navigate(Routes.Notes.route) {
                        popUpTo(Routes.Notes.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.EditNote,
                        contentDescription = null
                    )
                },
                label = { Text("Notes") }
            )

            NavigationBarItem(
                selected = scaffoldRoute == Routes.Todos.route,
                onClick = {
                    navController.navigate(Routes.Todos.route) {
                        popUpTo(Routes.Notes.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                },
                label = { Text("Todos") }
            )
        }
    }
}
