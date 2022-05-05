package ru.sysor.composecleanarch.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.sysor.composecleanarch.framework.NoteViewModel
import ru.sysor.composecleanarch.presentation.ui.screens.ItemScreen
import ru.sysor.composecleanarch.presentation.ui.screens.ListScreen
import ru.sysor.composecleanarch.presentation.ui.screens.Screen

@Composable
fun Navigation(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen.route
    ){
        composable(Screen.ListScreen.route){
            ListScreen(viewModel, navController = navController)
        }
        composable(
            route = Screen.ItemScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1
                    nullable = false
                })
        ) { entry ->
            entry.arguments?.getLong("id")?.let {
                ItemScreen(viewModel, id = it)
            }
        }
    }
}