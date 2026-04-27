package com.example.sqlite_connection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sqlite_connection.ui.theme.SqliteconnectionTheme

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SqliteconnectionTheme {
                UIAddProductActivity()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIAddProductActivity() {
    val context = LocalContext.current
    val activity = context as AddProductActivity
    val db = remember { ProductDatabaseHelper(context) }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errors by remember { mutableStateOf(ProductFormErrors()) }
    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = { activity.finish() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (submitted) errors = validateProductForm(it, price, category, stock)
                },
                label = { Text("Nombre del producto") },
                isError = errors.name != null,
                supportingText = errors.name?.let { msg -> { Text(msg) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                    if (submitted) errors = validateProductForm(name, it, category, stock)
                },
                label = { Text("Precio ($)") },
                isError = errors.price != null,
                supportingText = errors.price?.let { msg -> { Text(msg) } },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )
            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it
                    if (submitted) errors = validateProductForm(name, price, it, stock)
                },
                label = { Text("Categoría") },
                isError = errors.category != null,
                supportingText = errors.category?.let { msg -> { Text(msg) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = stock,
                onValueChange = {
                    stock = it
                    if (submitted) errors = validateProductForm(name, price, category, it)
                },
                label = { Text("Stock (unidades)") },
                isError = errors.stock != null,
                supportingText = errors.stock?.let { msg -> { Text(msg) } },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    submitted = true
                    val validation = validateProductForm(name, price, category, stock)
                    errors = validation
                    if (!validation.hasErrors) {
                        db.insert(
                            Product(
                                name = name.trim(),
                                price = price.toDouble(),
                                category = category.trim(),
                                stock = stock.toInt()
                            )
                        )
                        activity.finish()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Producto")
            }
        }
    }
}
