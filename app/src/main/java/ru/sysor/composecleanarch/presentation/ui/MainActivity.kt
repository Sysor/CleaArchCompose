package ru.sysor.composecleanarch.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import kotlinx.coroutines.*
import ru.sysor.composecleanarch.framework.NoteViewModel
import ru.sysor.composecleanarch.presentation.Navigation
import ru.sysor.composecleanarch.presentation.theme.ComposeCleanArchTheme
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    val viewModel: NoteViewModel by viewModels()

    private val job by lazy { Job() }
    private val context: CoroutineContext
        get() = job + Dispatchers.Main + handler

    private val handler = CoroutineExceptionHandler{_, throwable -> Log.e("err", "$throwable")}
    val scope = CoroutineScope(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCleanArchTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(viewModel)
                }
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
            scope.launch(Dispatchers.Default){
                Log.v("test", "test")
            }
        return super.onCreateView(name, context, attrs)
    }

    open class test(val item: String){}

    interface testInterface{
        fun getItem()
    }

    data class test1(val item1: String): test(item1), testInterface {
        override fun getItem() {
            TODO("Not yet implemented")
        }
    }
}