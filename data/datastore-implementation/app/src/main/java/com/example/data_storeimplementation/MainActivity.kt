package com.example.data_storeimplementation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.data_storeimplementation.ui.theme.DatastoreimplementationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


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

fun readLeer(context: Context): Flow<String> {
    return context.dataStore.data.map { prefs ->
        prefs[clave] ?: ""
    }
}

suspend fun saveGuardar(context: Context, valueValor: String) {
    context.dataStore.edit { prefs ->
        prefs[clave] = valueValor
    }
}


@Composable
fun ourUI(context: Context) {
    val scope = rememberCoroutineScope()
    var valueValor by remember {
        mutableStateOf("")
    }
    val usrFlow = readLeer(context)
    val usrSave by usrFlow.collectAsState(initial = "")

    // Add here widgets
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display current saved value
        Text(text = "Valor guardado: $usrSave")

        Spacer(modifier = Modifier.height(16.dp))

        // TextField for input
        TextField(
            value = valueValor,
            onValueChange = { newValue ->
                valueValor = newValue
            },
            label = {
                Text("Ingrese un valor")
            },
            modifier = Modifier.fillMaxSize(0.95f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to save value
        Button(
            onClick = {
                scope.launch {
                    saveGuardar(context, valueValor)
                }
            }
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DatastoreimplementationTheme {
        Greeting("Android")
    }
}