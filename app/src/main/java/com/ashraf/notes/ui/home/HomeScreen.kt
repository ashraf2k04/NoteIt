package com.ashraf.notes.ui.home

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ashraf.notes.ui.components.AddItemDialog
import com.ashraf.notes.ui.components.DynamicFab
import com.ashraf.notes.ui.navigation.AppNavGraph
import com.ashraf.notes.ui.navigation.Routes
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var scaffoldRoute by remember { mutableStateOf(currentRoute) }

    LaunchedEffect(currentRoute) {
        delay(16)
        scaffoldRoute = currentRoute
    }

    val isEditScreen =
        scaffoldRoute?.startsWith("edit_note") == true ||
                scaffoldRoute?.startsWith("edit_todo") == true

    val showFab =
        scaffoldRoute == Routes.Notes.route ||
                scaffoldRoute == Routes.Todos.route

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets(0),

        topBar = {
            if (!isEditScreen) {
                CenterAlignedTopAppBar(
                    title = { Text("NoteIT") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        },

        floatingActionButton = {
            if (showFab) {
                DynamicFab { showDialog = true }
            }
        },

        bottomBar = {
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
                        label = { Text("Notes") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            selectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
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
                        label = { Text("Todos") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            selectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AppNavGraph(navController)

            if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                showDialog &&
                !isEditScreen
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            renderEffect =
                                android.graphics.RenderEffect
                                    .createBlurEffect(
                                        8f,
                                        8f,
                                        android.graphics.Shader.TileMode.CLAMP
                                    )
                                    .asComposeRenderEffect()
                        }
                )
            }
        }

        if (showDialog) {
            when (scaffoldRoute) {
                Routes.Notes.route -> {
                    AddItemDialog(
                        title = " Create New Note",
                        hint = "Note title",
                        onDismiss = { showDialog = false },
                        onConfirm = { noteTitle ->
                            showDialog = false
                            navController.navigate(
                                Routes.EditNote.create(-1, noteTitle)
                            )
                        }
                    )
                }

                Routes.Todos.route -> {
                    AddItemDialog(
                        title = "Create New Todo",
                        hint = "Todo title",
                        onDismiss = { showDialog = false },
                        onConfirm = { todoTitle ->
                            showDialog = false
                            navController.navigate(
                                Routes.EditTodo.create(-1, todoTitle)
                            )
                        }
                    )
                }
            }
        }
    }
}
