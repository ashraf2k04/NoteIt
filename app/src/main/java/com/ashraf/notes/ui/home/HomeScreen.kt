package com.ashraf.notes.ui.home

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ashraf.notes.ui.components.AddItemDialog
import com.ashraf.notes.ui.components.DynamicFab
import com.ashraf.notes.ui.navigation.AppNavGraph
import com.ashraf.notes.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {


    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showFab = currentRoute == Routes.Notes.route ||
            currentRoute == Routes.Todos.route

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (
                currentRoute?.startsWith("edit_note") != true &&
                currentRoute?.startsWith("edit_todo") != true
            ) {
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
            if (currentRoute == Routes.Notes.route || currentRoute == Routes.Todos.route) {
               NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Routes.Notes.route,
                        onClick = {
                            navController.navigate(Routes.Notes.route) {
                                popUpTo(Routes.Notes.route) { inclusive = true }
                            }
                        },
                        icon = { Icon(Icons.Default.AddCircle, null) },
                        label = { Text("Notes") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Routes.Todos.route,
                        onClick = {
                            navController.navigate(Routes.Todos.route)
                        },
                        icon = { Icon(Icons.Default.CheckCircle, null) },
                        label = { Text("Todos") }
                    )
                }
            }

        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && showDialog) {
                        Modifier.graphicsLayer {
                            renderEffect =
                                android.graphics.RenderEffect
                                    .createBlurEffect(
                                        8f,
                                        8f,
                                        android.graphics.Shader.TileMode.CLAMP
                                    )
                                    .asComposeRenderEffect()
                        }
                    } else Modifier
                    )) {
            AppNavGraph(navController)
        }

        if (showDialog) {
            when (currentRoute) {

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


