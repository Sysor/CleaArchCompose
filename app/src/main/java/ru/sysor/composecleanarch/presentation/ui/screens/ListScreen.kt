package ru.sysor.composecleanarch.presentation.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sysor.composecleanarch.framework.NoteViewModel
import ru.sysor.core.data.Note

@Composable
fun ListScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes = remember { mutableStateOf(emptyList<Note>())}
    val progress = remember { mutableStateOf(false)}

    LaunchedEffect(key1 = "test") {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            progress.value = true
            notes.value = viewModel.useCases.getAllNotes().toList()
            progress.value = false
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.ItemScreen.route + "/" + 0)}
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(notes.value){ item ->
                ListItem(item, navController)
                Divider(color = Color.Black, thickness = 2.dp)
            }
        }
        ProgressBarIndicator(progress.value)
    }
}

@Composable
fun ListItem(note: Note, navController: NavController) {
    Text(
        text = note.title, style = MaterialTheme.typography.h3,
        modifier = Modifier.clickable {
            navController.navigate(Screen.ItemScreen.route + "/" + note.id)
        })
}

@Composable
fun ProgressBarIndicator(visible: Boolean) {
    if(visible) {
        var progress by remember { mutableStateOf(0.1f)}
        val progressValue = animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec).value
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(/*progress = progressValue*/)
        }
    }
}
