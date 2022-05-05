package ru.sysor.composecleanarch.presentation.ui.screens

sealed class Screen(val route: String){
    object ItemScreen : Screen("item_screen")
    object ListScreen : Screen("list_screen")
}
