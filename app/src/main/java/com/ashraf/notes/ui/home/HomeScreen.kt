package com.ashraf.notes.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ashraf.notes.ui.home.components.DynamicFab
import com.ashraf.notes.ui.home.components.BottomNavigationBar
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

    Scaffold(
        contentWindowInsets = WindowInsets(0,0,0,0),

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
                DynamicFab {
                    when (scaffoldRoute) {
                        Routes.Notes.route -> {
                            navController.navigate(
                                Routes.EditNote.create(-1)
                            )
                        }

                        Routes.Todos.route -> {
                            navController.navigate(
                                Routes.EditTodo.create(-1)
                            )
                        }
                    }
                }
            }
        },

        bottomBar = {
            BottomNavigationBar( scaffoldRoute, navController )
        }
    )
    { padding ->
            AppNavGraph(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
    }
}
