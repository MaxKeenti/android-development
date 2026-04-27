package com.example.data_storeimplementation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data_storeimplementation.ui.theme.DatastoreimplementationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

val Context.dataStore by preferencesDataStore("Configuración")
val clave = stringPreferencesKey("ejemplo")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DatastoreimplementationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun readLeer (context:Context): Flow<String> {
    return context.dataStore.data.map {
        prefs -> prefs[clave] ? : ""
    }
    suspend saveGuardar (context:Context, valueValor:String){
        context.dataStore.edit{
            prefs -> prefs [clave] = valueValor
        }
    }
}

@Composable
fun ourUI(context:Context) {
    val scope = rememberCoroutineScope()
    var valueValor by remember {
        mutableStateOf("")
    }
    val usrFlow = readLeer(context)
    val usrSave by usrFlow.colectAsState(initial = "")

    //Add here widgets

    TextField(
        value = valueValor,
        onValueChange = {
            valueValor = it
        },
        label = {
            Text("Valor")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DatastoreimplementationTheme {
        Greeting("Android")
    }
}