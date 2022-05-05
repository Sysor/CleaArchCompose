package ru.sysor.composecleanarch.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sysor.composecleanarch.framework.NoteViewModel
import ru.sysor.core.data.Note

@Composable
fun ItemScreen(viewModel: NoteViewModel, id: Long) {
    val currentNote = remember { mutableStateOf(Note())}
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("error")}
    val message = remember { mutableStateOf("")}
    val titleState = remember { mutableStateOf(TextFieldValue(currentNote.value.title))}
    val contentState = remember { mutableStateOf(TextFieldValue(currentNote.value.content))}
    val saved by viewModel.saved.observeAsState(viewModel.saved.value)

    LaunchedEffect(key1 = "test"){
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.useCases.getNote(id)?.let {
                currentNote.value = it
                titleState.value = TextFieldValue(currentNote.value.title)
                contentState.value = TextFieldValue(currentNote.value.content)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                if(currentNote.value.title.isEmpty()){
                    message.value = "title is empty"
                    setShowDialog(true)
                    return@FloatingActionButton
                }

                if(currentNote.value.content.isEmpty()){
                    message.value = "content is empty"
                    setShowDialog(true)
                    return@FloatingActionButton
                }
                if(currentNote.value.id == 0L){
                    currentNote.value.creationTime = System.currentTimeMillis()
                } else {
                    currentNote.value.updateTime = System.currentTimeMillis()
                }
                viewModel.saveNote(currentNote.value)
            }) {
                Icon(Icons.Filled.Check, contentDescription = "Save")
                Dialog(show = showDialog, title = title.value, text = message.value, setShowDialog)
                ShowToast(show = saved ?: false, message = "saved", setShowToast = {viewModel.saved.postValue(it)})
            }
        }
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .clickable {

                }
        ) {
            Text(text = "Note")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleState.value,
                onValueChange = {
                    titleState.value = it
                    currentNote.value.title = it.text
                }
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(5.dp))
            TextField(
                value = contentState.value,
                singleLine = false,
                onValueChange = {
                    contentState.value = it
                    currentNote.value.content = it.text
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
fun ShowToast(show: Boolean, message: String, setShowToast: (Boolean) -> Unit) {
    if(show) {
        Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        setShowToast(false)
    }
}

@Composable
fun Dialog(show: Boolean, title: String, text: String, setShow : (Boolean) -> Unit) {
    if(show) {
        AlertDialog(onDismissRequest = {setShow(false)},
            title = { Text(title) },
            text = { Text(text) },
            confirmButton = {
                Button(
                    onClick = { setShow(false) }
                ){
                    Text("ok")
                }
            }
        )
    }
}